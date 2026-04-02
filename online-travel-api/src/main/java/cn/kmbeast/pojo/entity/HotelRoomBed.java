package cn.kmbeast.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 酒店房间床位实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class HotelRoomBed {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 床位号
     */
    private Integer number;

    /**
     * 房间ID
     */
    private Integer roomId;

    /**
     * 床位是否可用状态
     */
    private Boolean status;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
