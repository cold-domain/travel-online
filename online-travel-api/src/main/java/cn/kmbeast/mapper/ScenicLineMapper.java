package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.ScenicLineQueryDto;
import cn.kmbeast.pojo.entity.ScenicLine;
import cn.kmbeast.pojo.vo.ScenicLineVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 景点路线持久化接口
 */
@Mapper
public interface ScenicLineMapper {

    void save(ScenicLine scenicLine);

    void update(ScenicLine scenicLine);

    void batchDelete(@Param(value = "ids") List<Integer> ids);

    List<ScenicLineVO> query(ScenicLineQueryDto dto);

    Integer queryCount(ScenicLineQueryDto dto);

}
