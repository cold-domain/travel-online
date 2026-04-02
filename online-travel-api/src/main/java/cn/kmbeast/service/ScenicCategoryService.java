package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicCategoryQueryDto;
import cn.kmbeast.pojo.entity.ScenicCategory;

import java.util.List;

public interface ScenicCategoryService {

    Result<Void> save(ScenicCategory scenicCategory);

    Result<Void> update(ScenicCategory scenicCategory);

    Result<Void> batchDelete(List<Integer> ids);

    Result<List<ScenicCategory>> query(ScenicCategoryQueryDto dto);

}
