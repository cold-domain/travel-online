package cn.kmbeast.controller;

import cn.kmbeast.aop.Pager;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicCategoryQueryDto;
import cn.kmbeast.pojo.entity.ScenicCategory;
import cn.kmbeast.service.ScenicCategoryService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 景点分类的控制器
 */
@RestController
@RequestMapping("/scenicCategory")
public class ScenicCategoryController {

    // 视图层（controller） ---- 业务逻辑层（service） --- 持久化层（mapper）

    @Resource
    private ScenicCategoryService scenicCategoryService;

    /**
     * 景点分类新增
     *
     * @param scenicCategory 景点分类实体
     * @return Result<Void>
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public Result<Void> save(@RequestBody ScenicCategory scenicCategory) {
        return scenicCategoryService.save(scenicCategory);
    }

    /**
     * 景点分类修改
     *
     * @param scenicCategory 景点分类实体
     * @return Result<Void>
     */
    @PutMapping(value = "/update")
    @ResponseBody
    public Result<Void> update(@RequestBody ScenicCategory scenicCategory) {
        return scenicCategoryService.update(scenicCategory);
    }

    /**
     * 景点分类批量删除
     *
     * @param ids 景点分类Id列表
     * @return Result<Void>
     */
    @PostMapping(value = "/batchDelete")
    @ResponseBody
    public Result<Void> batchDelete(@RequestBody List<Integer> ids) {
        return scenicCategoryService.batchDelete(ids);
    }

    /**
     * 查询景点分类
     *
     * @return Result<Void>
     */
    @Pager
    @PostMapping(value = "/query")
    @ResponseBody
    public Result<List<ScenicCategory>> query(@RequestBody ScenicCategoryQueryDto dto) {
        return scenicCategoryService.query(dto);
    }

}
