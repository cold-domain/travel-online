package cn.kmbeast.controller;

import cn.kmbeast.aop.Pager;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicLineQueryDto;
import cn.kmbeast.pojo.entity.ScenicLine;
import cn.kmbeast.pojo.vo.ScenicLineVO;
import cn.kmbeast.service.ScenicLineService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 景点路线的控制器
 */
@RestController
@RequestMapping("/scenicLine")
public class ScenicLineController {

    @Resource
    private ScenicLineService scenicLineService;

    /**
     * 景点路线新增
     *
     * @param scenicLine 景点路线实体
     * @return Result<Void>
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public Result<Void> save(@RequestBody ScenicLine scenicLine) {
        return scenicLineService.save(scenicLine);
    }

    /**
     * 景点路线修改
     *
     * @param scenicLine 景点路线实体
     * @return Result<Void>
     */
    @PutMapping(value = "/update")
    @ResponseBody
    public Result<Void> update(@RequestBody ScenicLine scenicLine) {
        return scenicLineService.update(scenicLine);
    }

    /**
     * 景点路线批量删除
     *
     * @param ids 景点路线Id列表
     * @return Result<Void>
     */
    @PostMapping(value = "/batchDelete")
    @ResponseBody
    public Result<Void> batchDelete(@RequestBody List<Integer> ids) {
        return scenicLineService.batchDelete(ids);
    }

    /**
     * 查询景点路线
     *
     * @return Result<Void>
     */
    @Pager
    @PostMapping(value = "/query")
    @ResponseBody
    public Result<List<ScenicLineVO>> query(@RequestBody ScenicLineQueryDto dto) {
        return scenicLineService.query(dto);
    }

}
