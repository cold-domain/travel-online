package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicRatingQueryDto;
import cn.kmbeast.pojo.entity.ScenicLine;
import cn.kmbeast.pojo.entity.ScenicRating;
import cn.kmbeast.pojo.vo.ScenicRatingVO;

import java.util.List;

/**
 * 景点评分业务逻辑接口
 */
public interface ScenicRatingService {

    Result<Void> save(ScenicRating scenicRating);

    Result<List<ScenicRatingVO>> query(ScenicRatingQueryDto dto);

}
