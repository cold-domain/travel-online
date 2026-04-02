package cn.kmbeast.controller;

import cn.kmbeast.aop.Pager;
import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.VendorQueryDto;
import cn.kmbeast.pojo.entity.Vendor;
import cn.kmbeast.pojo.vo.VendorVO;
import cn.kmbeast.service.VendorService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 供应商的控制器
 */
@RestController
@RequestMapping("/vendor")
public class VendorController {

    @Resource
    private VendorService vendorService;

    /**
     * 供应商新增
     *
     * @param vendor 供应商实体
     * @return Result<Void>
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public Result<Void> save(@RequestBody Vendor vendor) {
        return vendorService.save(vendor);
    }

    /**
     * 供应商修改
     *
     * @param vendor 供应商实体
     * @return Result<Void>
     */
    @PutMapping(value = "/update")
    @ResponseBody
    public Result<Void> update(@RequestBody Vendor vendor) {
        return vendorService.update(vendor);
    }

    /**
     * 供应商批量删除
     *
     * @param ids 供应商Id列表
     * @return Result<Void>
     */
    @PostMapping(value = "/batchDelete")
    @ResponseBody
    public Result<Void> batchDelete(@RequestBody List<Integer> ids) {
        return vendorService.batchDelete(ids);
    }

    /**
     * 查询用户关联的供应商信息
     *
     * @return Result<Void>
     */
    @Pager
    @PostMapping(value = "/queryUser")
    @ResponseBody
    public Result<List<VendorVO>> queryUser() {
        VendorQueryDto queryDto = new VendorQueryDto();
        // 取得当前操作者的用户ID，并且设置上查询参数
        queryDto.setUserId(LocalThreadHolder.getUserId());
        return vendorService.query(queryDto);
    }

    /**
     * 查询供应商
     *
     * @return Result<Void>
     */
    @Pager
    @PostMapping(value = "/query")
    @ResponseBody
    public Result<List<VendorVO>> query(@RequestBody VendorQueryDto dto) {
        return vendorService.query(dto);
    }

}
