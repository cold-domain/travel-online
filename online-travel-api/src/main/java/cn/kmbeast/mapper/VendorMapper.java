package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.VendorQueryDto;
import cn.kmbeast.pojo.entity.Vendor;
import cn.kmbeast.pojo.vo.VendorVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 供应商持久化接口
 */
@Mapper
public interface VendorMapper {

    void save(Vendor vendor);

    void update(Vendor vendor);

    void batchDelete(@Param(value = "ids") List<Integer> ids);

    List<VendorVO> query(VendorQueryDto dto);

    Integer queryCount(VendorQueryDto dto);

}
