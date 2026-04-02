package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScenicTicketQueryDto extends QueryDto {

    private Integer id;

    private Integer scenicId;

    private Integer number;

    private BigDecimal price;

    private Double discount;

    private Boolean useStatus;

    private String detail;
}
