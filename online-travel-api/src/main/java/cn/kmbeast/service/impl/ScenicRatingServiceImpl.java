package cn.kmbeast.service.impl;

import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.mapper.ScenicRatingMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicRatingQueryDto;
import cn.kmbeast.pojo.entity.ScenicRating;
import cn.kmbeast.pojo.vo.ScenicRatingVO;
import cn.kmbeast.service.ScenicRatingService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 景点评分的业务逻辑实现类
 */
@Service
public class ScenicRatingServiceImpl implements ScenicRatingService {

    @Resource
    private ScenicRatingMapper scenicRatingMapper;

    /**
     * 景点评分新增
     *
     * @param scenicRating 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> save(ScenicRating scenicRating) {
        ScenicRatingQueryDto scenicRatingQueryDto = new ScenicRatingQueryDto();
        scenicRatingQueryDto.setScenicId(scenicRating.getScenicId());
        scenicRatingQueryDto.setUserId(LocalThreadHolder.getUserId());
        Integer queryCount = scenicRatingMapper.queryCount(scenicRatingQueryDto);
        if (queryCount != 0) {
            return ApiResult.error("已经评分过了");
        }
        // 设置评分时间
        scenicRating.setUserId(LocalThreadHolder.getUserId());
        scenicRating.setCreateTime(LocalDateTime.now());
        scenicRatingMapper.save(scenicRating);
        return ApiResult.success("评分成功");
    }

    /**
     * 景点评分查询
     *
     * @param dto 查询实体
     * @return Result<List < ScenicRatingVO>>
     */
    @Override
    public Result<List<ScenicRatingVO>> query(ScenicRatingQueryDto dto) {
        Integer totalCount = scenicRatingMapper.queryCount(dto);
        List<ScenicRatingVO> result = scenicRatingMapper.query(dto);
        return ApiResult.success(result, totalCount);
    }
}
