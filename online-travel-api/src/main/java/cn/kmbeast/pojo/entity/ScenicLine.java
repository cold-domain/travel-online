package cn.kmbeast.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 景点路线实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScenicLine {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 景点ID
     */
    private Integer scenicId;

    /**
     * 景点路线封面图
     */
    private String cover;

    /**
     * 路线描述
     */
    private String detail;

    /**
     * 层级
     */
    private Integer level;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
