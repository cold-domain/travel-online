package cn.kmbeast.service.impl;

import cn.kmbeast.constant.RedisKeyConstants;
import cn.kmbeast.mapper.NoticeMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.NoticeQueryDto;
import cn.kmbeast.pojo.entity.Notice;
import cn.kmbeast.redis.RedisCacheHelper;
import cn.kmbeast.redis.RedisPageCache;
import cn.kmbeast.service.NoticeService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 公告的业务逻辑实现类
 */
@Service
public class NoticeServiceImpl implements NoticeService {

    @Resource
    private NoticeMapper noticeMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisCacheHelper redisCacheHelper;

    /**
     * 公告新增
     *
     * @param notice 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> save(Notice notice) {
        // 设置初始时间
        notice.setCreateTime(LocalDateTime.now());
        noticeMapper.save(notice);
        redisCacheHelper.evictByPrefix(RedisKeyConstants.NOTICE_QUERY_CACHE_PREFIX);
        return ApiResult.success();
    }

    /**
     * 公告修改
     *
     * @param notice 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> update(Notice notice) {
        noticeMapper.update(notice);
        redisCacheHelper.evictByPrefix(RedisKeyConstants.NOTICE_QUERY_CACHE_PREFIX);
        return ApiResult.success();
    }

    /**
     * 公告删除
     *
     * @param ids 公告ID列表
     * @return Result<Void>
     */
    @Override
    public Result<Void> batchDelete(List<Integer> ids) {
        noticeMapper.batchDelete(ids);
        redisCacheHelper.evictByPrefix(RedisKeyConstants.NOTICE_QUERY_CACHE_PREFIX);
        return ApiResult.success();
    }

    /**
     * 公告查询
     *
     * @param dto 查询实体
     * @return Result<Notice>
     */
    @Override
    public Result<List<Notice>> query(NoticeQueryDto dto) {
        String cacheKey = redisCacheHelper.buildQueryCacheKey(RedisKeyConstants.NOTICE_QUERY_CACHE_PREFIX, dto);
        String cache = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cache != null) {
            RedisPageCache<Notice> pageCache = JSON.parseObject(
                    cache,
                    new TypeReference<RedisPageCache<Notice>>() {
                    }
            );
            return ApiResult.success(pageCache.getRecords(), pageCache.getTotal());
        }
        Integer totalCount = noticeMapper.queryCount(dto);
        List<Notice> result = noticeMapper.query(dto);
        stringRedisTemplate.opsForValue().set(
                cacheKey,
                JSON.toJSONString(new RedisPageCache<>(result, totalCount)),
                10,
                TimeUnit.MINUTES
        );
        return ApiResult.success(result, totalCount);
    }
}
