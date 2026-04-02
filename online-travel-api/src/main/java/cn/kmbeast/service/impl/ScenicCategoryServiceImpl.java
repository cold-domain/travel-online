package cn.kmbeast.service.impl;

import cn.kmbeast.constant.RedisKeyConstants;
import cn.kmbeast.mapper.ScenicCategoryMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicCategoryQueryDto;
import cn.kmbeast.pojo.entity.ScenicCategory;
import cn.kmbeast.redis.RedisCacheHelper;
import cn.kmbeast.redis.RedisPageCache;
import cn.kmbeast.service.ScenicCategoryService;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.TypeReference;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * 景点分类的业务逻辑实现类
 */
@Service
public class ScenicCategoryServiceImpl implements ScenicCategoryService {

    @Resource
    private ScenicCategoryMapper scenicCategoryMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisCacheHelper redisCacheHelper;

    /**
     * 景点分类新增
     *
     * @param scenicCategory 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> save(ScenicCategory scenicCategory) {
        scenicCategoryMapper.save(scenicCategory);
        redisCacheHelper.evictByPrefix(RedisKeyConstants.SCENIC_CATEGORY_QUERY_CACHE_PREFIX);
        return ApiResult.success();
    }

    /**
     * 景点分类修改
     *
     * @param scenicCategory 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> update(ScenicCategory scenicCategory) {
        scenicCategoryMapper.update(scenicCategory);
        redisCacheHelper.evictByPrefix(RedisKeyConstants.SCENIC_CATEGORY_QUERY_CACHE_PREFIX);
        return ApiResult.success();
    }

    /**
     * 景点分类删除
     *
     * @param ids 景点分类ID列表
     * @return Result<Void>
     */
    @Override
    public Result<Void> batchDelete(List<Integer> ids) {
        scenicCategoryMapper.batchDelete(ids);
        redisCacheHelper.evictByPrefix(RedisKeyConstants.SCENIC_CATEGORY_QUERY_CACHE_PREFIX);
        return ApiResult.success();
    }

    /**
     * 景点分类查询
     *
     * @param dto 查询实体
     * @return Result<ScenicCategory>
     */
    @Override
    public Result<List<ScenicCategory>> query(ScenicCategoryQueryDto dto) {
        String cacheKey = redisCacheHelper.buildQueryCacheKey(RedisKeyConstants.SCENIC_CATEGORY_QUERY_CACHE_PREFIX, dto);
        String cache = stringRedisTemplate.opsForValue().get(cacheKey);
        if (cache != null) {
            RedisPageCache<ScenicCategory> pageCache = JSON.parseObject(
                    cache,
                    new TypeReference<RedisPageCache<ScenicCategory>>() {
                    }
            );
            return ApiResult.success(pageCache.getRecords(), pageCache.getTotal());
        }
        Integer totalCount = scenicCategoryMapper.queryCount(dto);
        List<ScenicCategory> result = scenicCategoryMapper.query(dto);
        stringRedisTemplate.opsForValue().set(
                cacheKey,
                JSON.toJSONString(new RedisPageCache<>(result, totalCount)),
                30,
                TimeUnit.MINUTES
        );
        return ApiResult.success(result, totalCount);
    }
}
