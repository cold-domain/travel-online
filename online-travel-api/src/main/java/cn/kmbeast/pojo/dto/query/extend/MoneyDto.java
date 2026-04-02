package cn.kmbeast.pojo.dto.query.extend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class MoneyDto {

    private BigDecimal amount;

    private LocalDateTime payTime;
}
