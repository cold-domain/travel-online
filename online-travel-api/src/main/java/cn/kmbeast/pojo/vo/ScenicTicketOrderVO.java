package cn.kmbeast.pojo.vo;

import cn.kmbeast.pojo.entity.HotelOrderInfo;
import cn.kmbeast.pojo.entity.ScenicTicketOrder;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 景点订票信息的VO出参类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScenicTicketOrderVO extends ScenicTicketOrder {
    /**
     * 用户名
     */
    private String userName;
    /**
     * 景点ID
     */
    private Integer scenicId;
    /**
     * 景点名称
     */
    private String scenicName;
    /**
     * 门票介绍
     */
    private String detail;
    /**
     * 数量
     */
    private Integer number;
    /**
     * 单价
     */
    private Integer price;
    /**
     * 折扣
     */
    private Double discount;
}
