package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScenicTicketOrderQueryDto extends QueryDto {

    private Integer id;

    private Integer userId;

    private Integer ticketId;

    private Integer scenicId;

    private String concatPerson;

    private String concatPhone;

    private Integer buyNumber;

    private BigDecimal amount;

    private Boolean payStatus;

    private LocalDateTime payTime;
}
