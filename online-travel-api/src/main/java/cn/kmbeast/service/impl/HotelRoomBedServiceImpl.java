package cn.kmbeast.service.impl;

import cn.kmbeast.constant.RedisKeyConstants;
import cn.kmbeast.mapper.HotelRoomBedMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.HotelRoomBedQueryDto;
import cn.kmbeast.pojo.entity.HotelRoomBed;
import cn.kmbeast.redis.RedisCacheHelper;
import cn.kmbeast.redis.RedisPageCache;
import cn.kmbeast.service.HotelRoomBedService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 酒店房间床位的业务逻辑实现类
 */
@Service
public class HotelRoomBedServiceImpl implements HotelRoomBedService {

    @Resource
    private HotelRoomBedMapper hotelRoomBedMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisCacheHelper redisCacheHelper;

    /**
     * 酒店房间床位新增
     *
     * @param hotelRoomBed 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> save(HotelRoomBed hotelRoomBed) {
        // 设置初始时间
        hotelRoomBed.setCreateTime(LocalDateTime.now());
        hotelRoomBedMapper.save(hotelRoomBed);
        redisCacheHelper.evictByPrefix(RedisKeyConstants.HOTEL_ROOM_BED_QUERY_CACHE_PREFIX);
        return ApiResult.success();
    }

    /**
     * 酒店房间床位修改
     *
     * @param hotelRoomBed 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> update(HotelRoomBed hotelRoomBed) {
        hotelRoomBedMapper.update(hotelRoomBed);
        redisCacheHelper.evictByPrefix(RedisKeyConstants.HOTEL_ROOM_BED_QUERY_CACHE_PREFIX);
        return ApiResult.success();
    }

    /**
     * 酒店房间床位删除
     *
     * @param ids 酒店房间床位ID列表
     * @return Result<Void>
     */
    @Override
    public Result<Void> batchDelete(List<Integer> ids) {
        hotelRoomBedMapper.batchDelete(ids);
        redisCacheHelper.evictByPrefix(RedisKeyConstants.HOTEL_ROOM_BED_QUERY_CACHE_PREFIX);
        return ApiResult.success();
    }

    /**
     * 酒店房间床位查询
     *
     * @param dto 查询实体
     * @return Result<HotelRoomBed>
     */
    @Override
    public Result<List<HotelRoomBed>> query(HotelRoomBedQueryDto dto) {
        String cacheKey = redisCacheHelper.buildQueryCacheKey(RedisKeyConstants.HOTEL_ROOM_BED_QUERY_CACHE_PREFIX, dto);
        String cache = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cache != null) {
            RedisPageCache<HotelRoomBed> pageCache = JSON.parseObject(
                    cache,
                    new TypeReference<RedisPageCache<HotelRoomBed>>() {
                    }
            );
            return ApiResult.success(pageCache.getRecords(), pageCache.getTotal());
        }
        Integer totalCount = hotelRoomBedMapper.queryCount(dto);
        List<HotelRoomBed> result = hotelRoomBedMapper.query(dto);
        stringRedisTemplate.opsForValue().set(
                cacheKey,
                JSON.toJSONString(new RedisPageCache<>(result, totalCount)),
                30,
                TimeUnit.MINUTES
        );
        return ApiResult.success(result, totalCount);
    }
}
