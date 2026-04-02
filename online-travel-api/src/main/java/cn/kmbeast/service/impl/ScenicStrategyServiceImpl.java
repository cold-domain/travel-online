package cn.kmbeast.service.impl;

import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.mapper.ScenicStrategyMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicStrategyQueryDto;
import cn.kmbeast.pojo.entity.ScenicStrategy;
import cn.kmbeast.pojo.vo.ScenicStrategyListVO;
import cn.kmbeast.pojo.vo.ScenicStrategyVO;
import cn.kmbeast.service.ScenicStrategyService;
import cn.kmbeast.utils.TextUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * 景点攻略的业务逻辑实现类
 */
@Service
public class ScenicStrategyServiceImpl implements ScenicStrategyService {

    @Resource
    private ScenicStrategyMapper scenicStrategyMapper;

    /**
     * 景点攻略新增
     *
     * @param scenicStrategy 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> save(ScenicStrategy scenicStrategy) {
        // 设置初始时间
        scenicStrategy.setCreateTime(LocalDateTime.now());
        // 设置上发布者的用户ID
        scenicStrategy.setUserId(LocalThreadHolder.getUserId());
        // 默认未审核
        scenicStrategy.setIsAudit(false);
        scenicStrategyMapper.save(scenicStrategy);
        return ApiResult.success();
    }

    /**
     * 景点攻略修改
     *
     * @param scenicStrategy 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> update(ScenicStrategy scenicStrategy) {
        scenicStrategyMapper.update(scenicStrategy);
        return ApiResult.success();
    }

    /**
     * 景点攻略删除
     *
     * @param ids 景点攻略ID列表
     * @return Result<Void>
     */
    @Override
    public Result<Void> batchDelete(List<Integer> ids) {
        scenicStrategyMapper.batchDelete(ids);
        return ApiResult.success();
    }

    /**
     * 景点攻略查询
     *
     * @param dto 查询实体
     * @return Result<ScenicStrategyVO>
     */
    @Override
    public Result<List<ScenicStrategyVO>> query(ScenicStrategyQueryDto dto) {
        Integer totalCount = scenicStrategyMapper.queryCount(dto);
        List<ScenicStrategyVO> result = scenicStrategyMapper.query(dto);
        return ApiResult.success(result, totalCount);
    }

    /**
     * 景点攻略的审核
     *
     * @param id 景点攻略的ID
     * @return Result<Void>
     */
    @Override
    public Result<Void> audit(Integer id) {
        ScenicStrategy scenicStrategy = new ScenicStrategy();
        scenicStrategy.setId(id);
        // 设置审核状态
        scenicStrategy.setIsAudit(true);
        scenicStrategyMapper.update(scenicStrategy);
        return ApiResult.success("景点攻略审核成功");
    }

    /**
     * 查询攻略列表
     *
     * @param dto 查询参数
     * @return Result<List < ScenicStrategyListVO>>
     */
    @Override
    public Result<List<ScenicStrategyListVO>> queryList(ScenicStrategyQueryDto dto) {
        // 只能查已经审核通过的内容
        dto.setIsAudit(true);
        Integer totalCount = scenicStrategyMapper.queryCount(dto);
        List<ScenicStrategyVO> scenicStrategyVOS = scenicStrategyMapper.query(dto);
        List<ScenicStrategyListVO> scenicStrategyListVOS = new ArrayList<>();
        for (ScenicStrategyVO scenicStrategyVO : scenicStrategyVOS) {
            ScenicStrategyListVO scenicStrategyListVO = new ScenicStrategyListVO();
            BeanUtils.copyProperties(scenicStrategyVO, scenicStrategyListVO);
            // 处理简要 --- 现在是富文本类型 --- 需要处理
            String detail = TextUtils.extractText(scenicStrategyVO.getContent(), 200);
            scenicStrategyListVO.setDetail(detail);
            scenicStrategyListVOS.add(scenicStrategyListVO);
        }
        return ApiResult.success(scenicStrategyListVOS, totalCount);
    }
}
