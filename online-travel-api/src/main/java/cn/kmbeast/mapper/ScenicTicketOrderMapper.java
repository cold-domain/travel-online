package cn.kmbeast.mapper;

import cn.kmbeast.pojo.dto.query.extend.ScenicTicketOrderQueryDto;
import cn.kmbeast.pojo.dto.query.extend.ScenicTicketQueryParamDto;
import cn.kmbeast.pojo.entity.ScenicTicketOrder;
import cn.kmbeast.pojo.vo.ScenicTicketOrderVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 景区订单持久化接口
 */
@Mapper
public interface ScenicTicketOrderMapper {

    void save(ScenicTicketOrder scenicTicketOrder);

    void update(ScenicTicketOrder scenicTicketOrder);

    void batchDelete(@Param(value = "ids") List<Integer> ids);

    List<ScenicTicketOrderVO> query(ScenicTicketOrderQueryDto dto);

    Integer queryCount(ScenicTicketOrderQueryDto dto);

    List<ScenicTicketOrderVO> queryByScenicIds(ScenicTicketQueryParamDto paramDto);

    Integer queryCountByScenicIds(ScenicTicketQueryParamDto paramDto);

}
