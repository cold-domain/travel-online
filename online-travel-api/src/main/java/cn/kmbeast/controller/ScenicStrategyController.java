package cn.kmbeast.controller;

import cn.kmbeast.aop.Pager;
import cn.kmbeast.aop.Protector;
import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicStrategyQueryDto;
import cn.kmbeast.pojo.entity.ScenicStrategy;
import cn.kmbeast.pojo.vo.ScenicStrategyListVO;
import cn.kmbeast.pojo.vo.ScenicStrategyVO;
import cn.kmbeast.service.ScenicStrategyService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 景点攻略的控制器
 */
@RestController
@RequestMapping("/scenicStrategy")
public class ScenicStrategyController {

    @Resource
    private ScenicStrategyService scenicStrategyService;

    /**
     * 景点攻略新增
     *
     * @param scenicStrategy 景点攻略实体
     * @return Result<Void>
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public Result<Void> save(@RequestBody ScenicStrategy scenicStrategy) {
        return scenicStrategyService.save(scenicStrategy);
    }

    /**
     * 景点攻略审核
     *
     * @return Result<Void>
     */
    @Protector(role = "管理员")
    @PostMapping(value = "/audit/{id}")
    @ResponseBody
    public Result<Void> audit(@PathVariable Integer id) {
        return scenicStrategyService.audit(id);
    }

    /**
     * 景点攻略修改
     *
     * @param scenicStrategy 景点攻略实体
     * @return Result<Void>
     */
    @PutMapping(value = "/update")
    @ResponseBody
    public Result<Void> update(@RequestBody ScenicStrategy scenicStrategy) {
        return scenicStrategyService.update(scenicStrategy);
    }

    /**
     * 景点攻略批量删除
     *
     * @param ids 景点攻略Id列表
     * @return Result<Void>
     */
    @PostMapping(value = "/batchDelete")
    @ResponseBody
    public Result<Void> batchDelete(@RequestBody List<Integer> ids) {
        return scenicStrategyService.batchDelete(ids);
    }

    /**
     * 查询攻略列表
     *
     * @return Result<List < ScenicStrategyListVO>>
     */
    @Pager
    @PostMapping(value = "/queryList")
    @ResponseBody
    public Result<List<ScenicStrategyListVO>> queryList(@RequestBody ScenicStrategyQueryDto dto) {
        return scenicStrategyService.queryList(dto);
    }

    /**
     * 查询用户发表的景点攻略
     *
     * @return Result<Void>
     */
    @Pager
    @PostMapping(value = "/queryUser")
    @ResponseBody
    public Result<List<ScenicStrategyVO>> queryUser(@RequestBody ScenicStrategyQueryDto dto) {
        // 设置上用户ID，数据隔离
        dto.setUserId(LocalThreadHolder.getUserId());
        return scenicStrategyService.query(dto);
    }


    /**
     * 查询景点攻略
     *
     * @return Result<Void>
     */
    @Pager
    @PostMapping(value = "/query")
    @ResponseBody
    public Result<List<ScenicStrategyVO>> query(@RequestBody ScenicStrategyQueryDto dto) {
        return scenicStrategyService.query(dto);
    }

}
