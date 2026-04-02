package cn.kmbeast.pojo.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 景点分类实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ScenicCategory {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 分类名
     */
    private String name;

}
