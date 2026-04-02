package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class HotelQueryDto extends QueryDto {

    private Integer id;

    private Integer vendorId;

    private String name;

    private String cover;

    private String address;

    private String concatPhone;
}
