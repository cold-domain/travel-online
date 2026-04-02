package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class EvaluationsQueryDto extends QueryDto {

    private Integer id;

    private Integer userId;

    private Integer contentId;

    private Integer parentId;

    private Integer commenterId;

    private Integer replierId;

    private String contentType;

    private String content;
}
