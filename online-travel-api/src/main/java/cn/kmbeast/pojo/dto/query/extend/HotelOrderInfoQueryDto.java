package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class HotelOrderInfoQueryDto extends QueryDto {

    private Integer id;

    private Integer roomId;

    private Integer userId;

    private String concatPerson;

    private String concatPhone;

    private BigDecimal amount;

    private Boolean payStatus;

    private LocalDateTime payTime;
}
