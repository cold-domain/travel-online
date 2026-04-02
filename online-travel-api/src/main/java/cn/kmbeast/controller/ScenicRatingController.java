package cn.kmbeast.controller;

import cn.kmbeast.aop.Pager;
import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicRatingQueryDto;
import cn.kmbeast.pojo.entity.ScenicRating;
import cn.kmbeast.pojo.vo.ScenicRatingVO;
import cn.kmbeast.service.ScenicRatingService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * 景点评分的控制器
 */
@RestController
@RequestMapping("/scenicRating")
public class ScenicRatingController {

    @Resource
    private ScenicRatingService scenicRatingService;

    /**
     * 景点评分新增
     *
     * @param scenicRating 景点评分实体
     * @return Result<Void>
     */
    @PostMapping(value = "/save")
    @ResponseBody
    public Result<Void> save(@RequestBody ScenicRating scenicRating) {
        return scenicRatingService.save(scenicRating);
    }

    /**
     * 查询景点评分
     *
     * @return Result<List<ScenicRatingVO>>
     */
    @Pager
    @PostMapping(value = "/query")
    @ResponseBody
    public Result<List<ScenicRatingVO>> query(@RequestBody ScenicRatingQueryDto dto) {
        return scenicRatingService.query(dto);
    }

    /**
     * 查询用户对于景点的评分
     *
     * @return Result<List<ScenicRatingVO>>
     */
    @Pager
    @PostMapping(value = "/queryUser")
    @ResponseBody
    public Result<List<ScenicRatingVO>> queryUser(@RequestBody ScenicRatingQueryDto dto) {
        dto.setUserId(LocalThreadHolder.getUserId());
        return scenicRatingService.query(dto);
    }

}
