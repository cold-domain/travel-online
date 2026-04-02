package cn.kmbeast.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 酒店房间实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelRoom {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 房间号
     */
    private String name;

    /**
     * 酒店ID
     */
    private Integer hotelId;

    /**
     * 图片
     */
    private String cover;

    /**
     * 价格
     */
    private BigDecimal price;

    /**
     * 折扣
     */
    private Double discount;

    /**
     * 房间描述
     */
    private String detail;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
