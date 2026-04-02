package cn.kmbeast.pojo.dto.query.extend;

import cn.kmbeast.pojo.dto.query.base.QueryDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserQueryDto extends QueryDto {

    private Integer id;

    private Integer role;

    private Integer userRole;

    private String userAccount;

    private String userName;

    private String userPwd;

    private String userAvatar;

    private String userEmail;

    private Boolean isLogin;

    private Boolean isWord;
}
