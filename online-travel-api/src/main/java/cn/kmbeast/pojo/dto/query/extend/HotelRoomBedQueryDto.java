package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HotelRoomBedQueryDto extends QueryDto {

    private Integer id;

    private Integer roomId;

    private Integer number;

    private Boolean status;
}
