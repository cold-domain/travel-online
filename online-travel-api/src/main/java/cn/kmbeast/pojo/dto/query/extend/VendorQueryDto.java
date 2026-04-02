package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class VendorQueryDto extends QueryDto {

    private Integer id;

    private Integer userId;

    private String name;

    private String concatPerson;

    private String concatPhone;

    private String email;

    private String productType;

    private String workAddress;

    private Boolean isAudit;

    private Boolean status;
}
