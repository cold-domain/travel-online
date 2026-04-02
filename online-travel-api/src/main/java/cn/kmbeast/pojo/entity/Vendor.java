package cn.kmbeast.pojo.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * 供应商/服务商实体
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Vendor {

    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 供应商的名称
     */
    private String name;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 办公地址
     */
    private String workAddress;

    /**
     * 产品类型
     */
    private String productType;

    /**
     * 用户ID
     */
    private Integer userId;

    /**
     * 联系人
     */
    private String concatPerson;

    /**
     * 联系电话
     */
    private String concatPhone;

    /**
     * 是否已经审核
     */
    private Boolean isAudit;

    /**
     * 供应商状态
     */
    private Boolean status;

    /**
     * 创建时间
     */
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;
}
