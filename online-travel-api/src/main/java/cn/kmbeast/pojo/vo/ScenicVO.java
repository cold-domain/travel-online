package cn.kmbeast.pojo.vo;

import cn.kmbeast.pojo.entity.Scenic;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 景点VO类
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ScenicVO extends Scenic {
    /**
     * 分类名
     */
    private String categoryName;
    /**
     * 供应商名称
     */
    private String vendorName;
    /**
     * 评分（平均分）
     */
    private Double ratingScore;
}
