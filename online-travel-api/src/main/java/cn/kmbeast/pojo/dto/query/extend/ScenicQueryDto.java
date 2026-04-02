package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class ScenicQueryDto extends QueryDto {

    private Integer id;

    private Integer categoryId;

    private Integer vendorId;

    private String name;

    private String cover;

    private String detail;

    private String saveIds;

    private String viewIds;

    private String address;

    private Boolean status;
}
