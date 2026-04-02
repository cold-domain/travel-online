package cn.kmbeast.service.impl;

import cn.kmbeast.constant.RedisKeyConstants;
import cn.kmbeast.mapper.*;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.*;
import cn.kmbeast.pojo.vo.ChartVO;
import cn.kmbeast.service.ViewsService;
import com.alibaba.fastjson2.JSON;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 首页可视化
 */
@Service
public class ViewsServiceImpl implements ViewsService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private ScenicMapper scenicMapper;
    @Resource
    private HotelMapper hotelMapper;
    @Resource
    private ScenicStrategyMapper scenicStrategyMapper;
    @Resource
    private ScenicTicketOrderMapper scenicTicketOrderMapper;
    @Resource
    private HotelOrderInfoMapper hotelOrderInfoMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 统计一些系统的基础数据
     *
     * @return Result<List < ChartVO>>
     */
    @Override
    public Result<List<ChartVO>> staticControls() {
        String cache = stringRedisTemplate.opsForValue().get(RedisKeyConstants.VIEWS_STATIC_CONTROLS_CACHE);
        if (cache != null) {
            return ApiResult.success(JSON.parseArray(cache, ChartVO.class));
        }
        List<ChartVO> chartVOS = new ArrayList<>();
        // 1. 用户数
        UserQueryDto userQueryDto = new UserQueryDto();
        int userCount = userMapper.queryCount(userQueryDto);
        change(userCount, "存量用户", chartVOS);
        // 2. 收录景点
        Integer scenicCount = scenicMapper.queryCount(new ScenicQueryDto());
        change(scenicCount, "收录景点", chartVOS);
        // 3. 入驻酒店
        Integer hotelCount = hotelMapper.queryCount(new HotelQueryDto());
        change(hotelCount, "入驻酒店", chartVOS);
        // 4. 收录攻略
        Integer strategyCount = scenicStrategyMapper.queryCount(new ScenicStrategyQueryDto());
        change(strategyCount, "收录攻略", chartVOS);
        // 4. 门票订单
        Integer scenicTicketOrderCount = scenicTicketOrderMapper.queryCount(new ScenicTicketOrderQueryDto());
        change(scenicTicketOrderCount, "门票订单", chartVOS);
        // 4. 酒店订单
        Integer hotelOrderInfoCount = hotelOrderInfoMapper.queryCount(new HotelOrderInfoQueryDto());
        change(hotelOrderInfoCount, "酒店订单", chartVOS);
        stringRedisTemplate.opsForValue().set(
                RedisKeyConstants.VIEWS_STATIC_CONTROLS_CACHE,
                JSON.toJSONString(chartVOS),
                5,
                TimeUnit.MINUTES
        );
        return ApiResult.success(chartVOS);
    }

    /**
     * 参数处理
     *
     * @param count    总数目
     * @param name     统计项
     * @param chartVOS 装它们的集合
     */
    private void change(Integer count, String name, List<ChartVO> chartVOS) {
        ChartVO chartVO = new ChartVO(name, count);
        chartVOS.add(chartVO);
    }


}
