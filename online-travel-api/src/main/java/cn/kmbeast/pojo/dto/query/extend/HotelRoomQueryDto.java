package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
public class HotelRoomQueryDto extends QueryDto {

    private Integer id;

    private Integer hotelId;

    private List<Integer> hotelIds;

    private String name;

    private String cover;

    private BigDecimal price;

    private Double discount;

    private String detail;
}
