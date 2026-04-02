package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.ScenicLineQueryDto;
import cn.kmbeast.pojo.dto.query.extend.ScenicRatingQueryDto;
import cn.kmbeast.pojo.entity.ScenicLine;
import cn.kmbeast.pojo.entity.ScenicRating;
import cn.kmbeast.pojo.vo.ScenicLineVO;
import cn.kmbeast.pojo.vo.ScenicRatingVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 景点评分持久化接口
 */
@Mapper
public interface ScenicRatingMapper {

    void save(ScenicRating scenicRating);

    List<ScenicRatingVO> query(ScenicRatingQueryDto dto);

    Integer queryCount(ScenicRatingQueryDto dto);

}
