package cn.kmbeast.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 数据源VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChartVO {
    /**
     * 描述项：可以是时间，也可以是具体的统计项
     */
    private String name;
    /**
     * 数据总数
     */
    private Object count;
}
