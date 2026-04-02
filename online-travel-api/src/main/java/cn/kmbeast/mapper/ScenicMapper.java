package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.ScenicQueryDto;
import cn.kmbeast.pojo.entity.Scenic;
import cn.kmbeast.pojo.vo.ScenicVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 景点持久化接口
 */
@Mapper
public interface ScenicMapper {

    void save(Scenic scenic);

    void updateSaveIds(Scenic scenic);

    void update(Scenic scenic);

    void batchDelete(@Param(value = "ids") List<Integer> ids);

    List<ScenicVO> query(ScenicQueryDto dto);

    Integer queryCount(ScenicQueryDto dto);

}
