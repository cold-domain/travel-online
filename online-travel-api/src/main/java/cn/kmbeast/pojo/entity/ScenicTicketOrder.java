package cn.kmbeast.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 景点门票订单实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScenicTicketOrder {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 门票ID
     */
    private Integer ticketId;

    /**
     * 联系人
     */
    private String concatPerson;

    /**
     * 联系电话
     */
    private String concatPhone;

    /**
     * 金额
     */
    private BigDecimal amount;

    /**
     * 购买数量
     */
    private Integer buyNumber;

    /**
     * 支付状态
     */
    private Boolean payStatus;

    /**
     * 支付时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime payTime;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
