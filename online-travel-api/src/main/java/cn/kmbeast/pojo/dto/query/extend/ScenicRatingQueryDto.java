package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScenicRatingQueryDto extends QueryDto {

    private Integer id;

    private Integer scenicId;

    private Integer userId;

    private Integer score;
}
