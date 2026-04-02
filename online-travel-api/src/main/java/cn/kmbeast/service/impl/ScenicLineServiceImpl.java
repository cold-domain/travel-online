package cn.kmbeast.service.impl;

import cn.kmbeast.mapper.ScenicLineMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicLineQueryDto;
import cn.kmbeast.pojo.entity.ScenicLine;
import cn.kmbeast.pojo.vo.ScenicLineVO;
import cn.kmbeast.service.ScenicLineService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 景点路线的业务逻辑实现类
 */
@Service
public class ScenicLineServiceImpl implements ScenicLineService {

    @Resource
    private ScenicLineMapper scenicLineMapper;

    /**
     * 景点路线新增
     *
     * @param scenicLine 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> save(ScenicLine scenicLine) {
        // 设置初始时间
        scenicLine.setCreateTime(LocalDateTime.now());
        scenicLineMapper.save(scenicLine);
        return ApiResult.success();
    }

    /**
     * 景点路线修改
     *
     * @param scenicLine 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> update(ScenicLine scenicLine) {
        scenicLineMapper.update(scenicLine);
        return ApiResult.success();
    }

    /**
     * 景点路线删除
     *
     * @param ids 景点路线ID列表
     * @return Result<Void>
     */
    @Override
    public Result<Void> batchDelete(List<Integer> ids) {
        scenicLineMapper.batchDelete(ids);
        return ApiResult.success();
    }

    /**
     * 景点路线查询
     *
     * @param dto 查询实体
     * @return Result<ScenicVO>
     */
    @Override
    public Result<List<ScenicLineVO>> query(ScenicLineQueryDto dto) {
        Integer totalCount = scenicLineMapper.queryCount(dto);
        List<ScenicLineVO> result = scenicLineMapper.query(dto);
        return ApiResult.success(result, totalCount);
    }
}
