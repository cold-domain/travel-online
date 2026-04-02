package cn.kmbeast.service.impl;

import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.mapper.VendorMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.VendorQueryDto;
import cn.kmbeast.pojo.em.RoleEnum;
import cn.kmbeast.pojo.entity.Vendor;
import cn.kmbeast.pojo.vo.VendorVO;
import cn.kmbeast.service.VendorService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * 供应商的业务逻辑实现类
 */
@Service
public class VendorServiceImpl implements VendorService {

    @Resource
    private VendorMapper vendorMapper;

    /**
     * 供应商新增
     *
     * @param vendor 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> save(Vendor vendor) {
        if(!StringUtils.hasText(vendor.getName())){
            return ApiResult.error("供应商名称不能为空");
        }
        if(!StringUtils.hasText(vendor.getEmail())){
            return ApiResult.error("邮箱不能为空");
        }
        if(!StringUtils.hasText(vendor.getConcatPerson())){
            return ApiResult.error("联系人不能为空");
        }
        if(!StringUtils.hasText(vendor.getConcatPhone())){
            return ApiResult.error("联系电话不能为空");
        }
        if(!StringUtils.hasText(vendor.getProductType())){
            return ApiResult.error("产品类型不能为空");
        }
        // 如果是管理员才需要去校验法人
        if (Objects.equals(LocalThreadHolder.getRoleId(),
                RoleEnum.ADMIN.getRole())) {
            if(vendor.getUserId() == null){
                return ApiResult.error("法人不能为空");
            }
        }
        // 一个人只能由一条申请记录
        VendorQueryDto queryDto = new VendorQueryDto();
        queryDto.setUserId(vendor.getUserId());
        if (!Objects.equals(LocalThreadHolder.getRoleId(),
                RoleEnum.ADMIN.getRole())) {
            queryDto.setUserId(LocalThreadHolder.getUserId());
        }
        Integer count = vendorMapper.queryCount(queryDto);
        if (count != 0) {
            return ApiResult.error("该法人已绑定供应商，请复查");
        }
        // 设置创建时间
        vendor.setCreateTime(LocalDateTime.now());
        // 设置供应商初始的状态 --- 默认是好的，新增的时候只是没有审核
        vendor.setStatus(true);
        // 补充逻辑---如果是管理员新增的，法人就是传进来的ID，即用户ID
        // 反之，就是前端操作者自己操作的申请
        if (!Objects.equals(LocalThreadHolder.getRoleId(),
                RoleEnum.ADMIN.getRole())) {
            // 设置上用户ID
            vendor.setUserId(LocalThreadHolder.getUserId());
            // 用户申请，初始未审核
            vendor.setIsAudit(false);
        } else {
            // 管理员，默认就是审核的
            vendor.setIsAudit(true);
        }
        vendorMapper.save(vendor);
        return ApiResult.success();
    }

    /**
     * 供应商修改
     *
     * @param vendor 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> update(Vendor vendor) {
        vendorMapper.update(vendor);
        return ApiResult.success();
    }

    /**
     * 供应商删除
     *
     * @param ids 供应商ID列表
     * @return Result<Void>
     */
    @Override
    public Result<Void> batchDelete(List<Integer> ids) {
        vendorMapper.batchDelete(ids);
        return ApiResult.success();
    }

    /**
     * 供应商查询
     *
     * @param dto 查询实体
     * @return Result<Vendor>
     */
    @Override
    public Result<List<VendorVO>> query(VendorQueryDto dto) {
        Integer totalCount = vendorMapper.queryCount(dto);
        List<VendorVO> result = vendorMapper.query(dto);
        return ApiResult.success(result, totalCount);
    }
}
