package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.ScenicStrategyQueryDto;
import cn.kmbeast.pojo.entity.ScenicStrategy;
import cn.kmbeast.pojo.vo.ScenicStrategyVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 景点攻略持久化接口
 */
@Mapper
public interface ScenicStrategyMapper {

    void save(ScenicStrategy scenicStrategy);

    void update(ScenicStrategy scenicStrategy);

    void batchDelete(@Param(value = "ids") List<Integer> ids);

    List<ScenicStrategyVO> query(ScenicStrategyQueryDto dto);

    Integer queryCount(ScenicStrategyQueryDto dto);

}
