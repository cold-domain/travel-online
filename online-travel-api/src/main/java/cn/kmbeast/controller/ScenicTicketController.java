package cn.kmbeast.controller;

import cn.kmbeast.aop.Pager;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicTicketQueryDto;
import cn.kmbeast.pojo.entity.ScenicTicket;
import cn.kmbeast.pojo.vo.ScenicTicketVO;
import cn.kmbeast.service.ScenicTicketService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 景点门票的控制器
 */
@RestController
@RequestMapping("/scenicTicket")
public class ScenicTicketController {

    @Resource
    private ScenicTicketService scenicTicketService;

    /**
     * 景点门票新增
     *
     * @param scenicTicket 景点门票实体
     * @return Result<Void>
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public Result<Void> save(@RequestBody ScenicTicket scenicTicket) {
        return scenicTicketService.save(scenicTicket);
    }

    /**
     * 景点门票修改
     *
     * @param scenicTicket 景点门票实体
     * @return Result<Void>
     */
    @PutMapping(value = "/update")
    @ResponseBody
    public Result<Void> update(@RequestBody ScenicTicket scenicTicket) {
        return scenicTicketService.update(scenicTicket);
    }

    /**
     * 景点门票批量删除
     *
     * @param ids 景点门票Id列表
     * @return Result<Void>
     */
    @PostMapping(value = "/batchDelete")
    @ResponseBody
    public Result<Void> batchDelete(@RequestBody List<Integer> ids) {
        return scenicTicketService.batchDelete(ids);
    }


    /**
     * 查询供应商所管理的景点门票信息
     *
     * @return Result<Void>
     */
    @PostMapping(value = "/queryScenicTicket")
    @ResponseBody
    public Result<List<ScenicTicketVO>> queryScenicTicket() {
        return scenicTicketService.queryScenicTicket();
    }

    /**
     * 查询景点门票
     *
     * @return Result<Void>
     */
    @Pager
    @PostMapping(value = "/query")
    @ResponseBody
    public Result<List<ScenicTicketVO>> query(@RequestBody ScenicTicketQueryDto dto) {
        return scenicTicketService.query(dto);
    }

}
