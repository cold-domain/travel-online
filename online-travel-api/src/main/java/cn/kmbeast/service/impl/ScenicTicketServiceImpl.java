package cn.kmbeast.service.impl;

import cn.kmbeast.constant.RedisKeyConstants;
import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.mapper.ScenicMapper;
import cn.kmbeast.mapper.ScenicTicketMapper;
import cn.kmbeast.mapper.VendorMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicQueryDto;
import cn.kmbeast.pojo.dto.query.extend.ScenicTicketQueryDto;
import cn.kmbeast.pojo.dto.query.extend.VendorQueryDto;
import cn.kmbeast.pojo.entity.Scenic;
import cn.kmbeast.pojo.entity.ScenicTicket;
import cn.kmbeast.pojo.vo.ScenicTicketVO;
import cn.kmbeast.pojo.vo.ScenicVO;
import cn.kmbeast.pojo.vo.VendorVO;
import cn.kmbeast.service.ScenicTicketService;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 景点门票的业务逻辑实现类
 */
@Service
public class ScenicTicketServiceImpl implements ScenicTicketService {

    @Resource
    private ScenicTicketMapper scenicTicketMapper;
    @Resource
    private ScenicMapper scenicMapper;
    @Resource
    private VendorMapper vendorMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 景点门票新增
     *
     * @param scenicTicket 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> save(ScenicTicket scenicTicket) {
        // 设置初始时间
        scenicTicket.setCreateTime(LocalDateTime.now());
        scenicTicketMapper.save(scenicTicket);
        if (scenicTicket.getId() != null) {
            stringRedisTemplate.delete(RedisKeyConstants.scenicTicketStockKey(scenicTicket.getId()));
        }
        return ApiResult.success();
    }

    /**
     * 景点门票修改
     *
     * @param scenicTicket 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> update(ScenicTicket scenicTicket) {
        scenicTicketMapper.update(scenicTicket);
        if (scenicTicket.getId() != null) {
            stringRedisTemplate.delete(RedisKeyConstants.scenicTicketStockKey(scenicTicket.getId()));
        }
        return ApiResult.success();
    }

    /**
     * 景点门票删除
     *
     * @param ids 景点门票ID列表
     * @return Result<Void>
     */
    @Override
    public Result<Void> batchDelete(List<Integer> ids) {
        scenicTicketMapper.batchDelete(ids);
        ids.forEach(id -> stringRedisTemplate.delete(RedisKeyConstants.scenicTicketStockKey(id)));
        return ApiResult.success();
    }

    /**
     * 景点门票查询
     *
     * @param dto 查询实体
     * @return Result<ScenicTicketVO>
     */
    @Override
    public Result<List<ScenicTicketVO>> query(ScenicTicketQueryDto dto) {
        Integer totalCount = scenicTicketMapper.queryCount(dto);
        List<ScenicTicketVO> result = scenicTicketMapper.query(dto);
        return ApiResult.success(result, totalCount);
    }

    /**
     * 查询供应商所管理的景点门票信息
     *
     * @return Result<Void>
     */
    @Override
    public Result<List<ScenicTicketVO>> queryScenicTicket() {
        // 用户名下的供应商信息ID
        Integer vendorId = getVendorId();
        ScenicQueryDto scenicQueryDto = new ScenicQueryDto();
        scenicQueryDto.setVendorId(vendorId);
        List<ScenicVO> scenicVOS = scenicMapper.query(scenicQueryDto);
        // 取出所有景点的ID
        List<Integer> scenicIds = scenicVOS.stream()
                .map(Scenic::getId)
                .collect(Collectors.toList());
        List<ScenicTicketVO> scenicTicketVOS =
                scenicTicketMapper.queryByScenicIds(scenicIds);

        return ApiResult.success(scenicTicketVOS);
    }


    private Integer getVendorId() {
        VendorQueryDto vendorQueryDto = new VendorQueryDto();
        // 由用户ID去查回来的供应商信息
        vendorQueryDto.setUserId(LocalThreadHolder.getUserId());
        List<VendorVO> vendorVOS = vendorMapper.query(vendorQueryDto);
        // 1. 要么真的没有 2. 有的话，只有一项
        if (vendorVOS.isEmpty()) {
            return null;
        }
        return vendorVOS.get(0).getId();
    }
}
