package cn.kmbeast.service.impl;

import cn.kmbeast.constant.RedisKeyConstants;
import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.mapper.ScenicMapper;
import cn.kmbeast.mapper.VendorMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicQueryDto;
import cn.kmbeast.pojo.dto.query.extend.VendorQueryDto;
import cn.kmbeast.pojo.em.RoleEnum;
import cn.kmbeast.pojo.entity.Scenic;
import cn.kmbeast.pojo.vo.ScenicVO;
import cn.kmbeast.pojo.vo.SelectedVO;
import cn.kmbeast.pojo.vo.VendorVO;
import cn.kmbeast.service.ScenicService;
import cn.kmbeast.utils.TextUtils;
import com.alibaba.fastjson2.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 景点的业务逻辑实现类
 */
@Service
public class ScenicServiceImpl implements ScenicService {

    @Resource
    private ScenicMapper scenicMapper;
    @Resource
    private VendorMapper vendorMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 景点新增
     *
     * @param scenic 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> save(Scenic scenic) {
        // 景点初始可用状态
        scenic.setStatus(true);
        // 设置初始时间
        scenic.setCreateTime(LocalDateTime.now());
        VendorQueryDto vendorQueryDto = new VendorQueryDto();
        // 如果是管理员，直接新增，供应商以及其他信息是已经传进来的
        if (Objects.equals(LocalThreadHolder.getRoleId(), RoleEnum.ADMIN.getRole())) {
            scenicMapper.save(scenic);
            invalidateSelectedCache();
            invalidateStaticControlsCache();
            return ApiResult.success();
        }
        // 这是用户自己新增的
        vendorQueryDto.setUserId(LocalThreadHolder.getUserId());
        List<VendorVO> vendorVOS = vendorMapper.query(vendorQueryDto);
        // 1. 要么真的没有 2. 有的话，只有一项
        if (vendorVOS.isEmpty()) {
            return ApiResult.error("供应商身份异常");
        }
        scenic.setVendorId(vendorVOS.get(0).getId());
        scenicMapper.save(scenic);
        invalidateSelectedCache();
        invalidateStaticControlsCache();
        return ApiResult.success();
    }

    /**
     * 景点修改
     *
     * @param scenic 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> update(Scenic scenic) {
        scenicMapper.update(scenic);
        invalidateSelectedCache();
        return ApiResult.success();
    }

    /**
     * 景点删除
     *
     * @param ids 景点ID列表
     * @return Result<Void>
     */
    @Override
    public Result<Void> batchDelete(List<Integer> ids) {
        scenicMapper.batchDelete(ids);
        for (Integer id : ids) {
            stringRedisTemplate.delete(RedisKeyConstants.scenicSaveUsersKey(id));
            stringRedisTemplate.delete(RedisKeyConstants.scenicViewUsersKey(id));
            stringRedisTemplate.opsForZSet().remove(RedisKeyConstants.SCENIC_HOT_RANK, String.valueOf(id));
        }
        invalidateSelectedCache();
        invalidateStaticControlsCache();
        return ApiResult.success();
    }

    /**
     * 景点查询
     *
     * @param dto 查询实体
     * @return Result<ScenicVO>
     */
    @Override
    public Result<List<ScenicVO>> query(ScenicQueryDto dto) {
        Integer totalCount = scenicMapper.queryCount(dto);
        List<ScenicVO> result = scenicMapper.query(dto);
        return ApiResult.success(result, totalCount);
    }

    /**
     * 供应商查询自己名下的景点信息
     *
     * @param dto 查询参数
     * @return Result<List < ScenicVO>>
     */
    @Override
    public Result<List<ScenicVO>> queryVendorScenic(ScenicQueryDto dto) {
        VendorQueryDto vendorQueryDto = new VendorQueryDto();
        vendorQueryDto.setUserId(LocalThreadHolder.getUserId());
        List<VendorVO> vendorVOS = vendorMapper.query(vendorQueryDto);
        // 1. 要么真的没有 2. 有的话，只有一项
        if (vendorVOS.isEmpty()) {
            return ApiResult.error("供应商身份异常");
        }
        dto.setVendorId(vendorVOS.get(0).getId());
        return query(dto);
    }

    /**
     * 关联景点下拉选择器
     *
     * @return Result<List < SelectedVO>>
     */
    @Override
    public Result<List<SelectedVO>> querySelectedScenic() {
        String cache = stringRedisTemplate.opsForValue().get(RedisKeyConstants.SELECTED_SCENIC_CACHE);
        if (StringUtils.hasText(cache)) {
            return ApiResult.success(JSON.parseArray(cache, SelectedVO.class));
        }
        ScenicQueryDto scenicQueryDto = new ScenicQueryDto();
        // 只能查可用状态的景点信息
        scenicQueryDto.setStatus(true);
        List<ScenicVO> scenicVOS = scenicMapper.query(scenicQueryDto);
        // 再次封装
        List<SelectedVO> selectedScenicList = scenicVOS.stream()
                .map(scenicVO -> new SelectedVO(
                        scenicVO.getId(),
                        scenicVO.getName()
                )).collect(Collectors.toList());
        stringRedisTemplate.opsForValue().set(
                RedisKeyConstants.SELECTED_SCENIC_CACHE,
                JSON.toJSONString(selectedScenicList),
                30,
                TimeUnit.MINUTES
        );
        return ApiResult.success(selectedScenicList);
    }

    @Override
    public Result<List<ScenicVO>> queryHot(Integer size) {
        Set<String> scenicIds = stringRedisTemplate.opsForZSet()
                .reverseRange(RedisKeyConstants.SCENIC_HOT_RANK, 0, Math.max(size, 1) - 1);
        if (CollectionUtils.isEmpty(scenicIds)) {
            return ApiResult.success(new ArrayList<>());
        }
        List<ScenicVO> scenicVOS = new ArrayList<>();
        for (String scenicId : scenicIds) {
            ScenicQueryDto scenicQueryDto = new ScenicQueryDto();
            scenicQueryDto.setId(Integer.valueOf(scenicId));
            List<ScenicVO> queryResult = scenicMapper.query(scenicQueryDto);
            if (!CollectionUtils.isEmpty(queryResult)) {
                scenicVOS.add(queryResult.get(0));
            }
        }
        return ApiResult.success(scenicVOS);
    }

    /**
     * 浏览操作
     *
     * @param scenicId 景点ID
     * @return Result<Void>
     */
    @Override
    public Result<Void> viewOperation(Integer scenicId) {
        ScenicVO scenicVO = getScenicById(scenicId);
        if (scenicVO == null) {
            return ApiResult.success();
        }
        Integer userId = LocalThreadHolder.getUserId();
        String cacheKey = RedisKeyConstants.scenicViewUsersKey(scenicId);
        warmScenicUserSet(cacheKey, scenicVO.getViewIds());
        Long added = stringRedisTemplate.opsForSet().add(cacheKey, String.valueOf(userId));
        if (!Long.valueOf(1L).equals(added)) {
            return ApiResult.success();
        }
        stringRedisTemplate.expire(cacheKey, 30, TimeUnit.DAYS);
        String newViewIds = TextUtils.join(scenicVO.getViewIds(), userId);
        Scenic scenic = new Scenic();
        scenic.setId(scenicId);
        scenic.setViewIds(newViewIds);
        scenicMapper.update(scenic);
        stringRedisTemplate.opsForZSet().incrementScore(
                RedisKeyConstants.SCENIC_HOT_RANK,
                String.valueOf(scenicId),
                1D
        );
        return ApiResult.success();
    }

    /**
     * 收藏操作 --- 收藏跟取消收藏是对立的
     *
     * @param scenicId 景点ID
     * @return Result<Void>
     */
    @Override
    public Result<Void> saveOperation(Integer scenicId) {
        ScenicVO scenicVO = getScenicById(scenicId);
        if (scenicVO == null) {
            return ApiResult.success();
        }
        Scenic scenic = new Scenic();
        scenic.setId(scenicVO.getId());
        Integer userId = LocalThreadHolder.getUserId();
        String cacheKey = RedisKeyConstants.scenicSaveUsersKey(scenicId);
        warmScenicUserSet(cacheKey, scenicVO.getSaveIds());
        Long removed = stringRedisTemplate.opsForSet().remove(cacheKey, String.valueOf(userId));
        boolean cancelSave = Long.valueOf(1L).equals(removed);
        scenic.setSaveIds(
                cancelSave ?
                        TextUtils.split(scenicVO.getSaveIds(), userId) :
                        TextUtils.join(scenicVO.getSaveIds(), userId)
        );
        scenicMapper.updateSaveIds(scenic);
        if (!cancelSave) {
            stringRedisTemplate.opsForSet().add(cacheKey, String.valueOf(userId));
            stringRedisTemplate.expire(cacheKey, 30, TimeUnit.DAYS);
        }
        stringRedisTemplate.opsForZSet().incrementScore(
                RedisKeyConstants.SCENIC_HOT_RANK,
                String.valueOf(scenicId),
                cancelSave ? -3D : 3D
        );
        return ApiResult.success(cancelSave ? "取消收藏成功" : "收藏成功");
    }

    /**
     * 查询用户的收藏景点状况
     *
     * @return Result<Void>
     */
    @Override
    public Result<Boolean> saveStatus(Integer scenicId) {
        ScenicVO scenicVO = getScenicById(scenicId);
        if (scenicVO == null) {
            return ApiResult.success();
        }
        String cacheKey = RedisKeyConstants.scenicSaveUsersKey(scenicId);
        warmScenicUserSet(cacheKey, scenicVO.getSaveIds());
        Boolean saved = stringRedisTemplate.opsForSet().isMember(
                cacheKey,
                String.valueOf(LocalThreadHolder.getUserId())
        );
        return ApiResult.success(Boolean.TRUE.equals(saved));
    }

    /**
     * 查询用户收藏的景点信息
     *
     * @return Result<List < ScenicVO>>
     */
    @Override
    public Result<List<ScenicVO>> querySave() {
        ScenicQueryDto scenicQueryDto = new ScenicQueryDto();
        // 设置用户ID
        Integer userId = LocalThreadHolder.getUserId();
        scenicQueryDto.setSaveIds(String.valueOf(userId));
        List<ScenicVO> scenicVOS = scenicMapper.query(scenicQueryDto);
        return ApiResult.success(scenicVOS);
    }

    private ScenicVO getScenicById(Integer scenicId) {
        ScenicQueryDto scenicQueryDto = new ScenicQueryDto();
        scenicQueryDto.setId(scenicId);
        List<ScenicVO> scenicVOS = scenicMapper.query(scenicQueryDto);
        if (CollectionUtils.isEmpty(scenicVOS)) {
            return null;
        }
        return scenicVOS.get(0);
    }

    private void warmScenicUserSet(String key, String userIds) {
        if (Boolean.TRUE.equals(stringRedisTemplate.hasKey(key))) {
            return;
        }
        if (!StringUtils.hasText(userIds)) {
            return;
        }
        List<String> members = Arrays.stream(userIds.split(","))
                .filter(StringUtils::hasText)
                .collect(Collectors.toList());
        if (members.isEmpty()) {
            return;
        }
        stringRedisTemplate.opsForSet().add(key, members.toArray(new String[0]));
        stringRedisTemplate.expire(key, 30, TimeUnit.DAYS);
    }

    private void invalidateSelectedCache() {
        stringRedisTemplate.delete(RedisKeyConstants.SELECTED_SCENIC_CACHE);
    }

    private void invalidateStaticControlsCache() {
        stringRedisTemplate.delete(RedisKeyConstants.VIEWS_STATIC_CONTROLS_CACHE);
    }
}
