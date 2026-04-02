package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicStrategyQueryDto;
import cn.kmbeast.pojo.entity.ScenicStrategy;
import cn.kmbeast.pojo.vo.ScenicStrategyListVO;
import cn.kmbeast.pojo.vo.ScenicStrategyVO;

import java.util.List;

public interface ScenicStrategyService {

    Result<Void> save(ScenicStrategy scenicStrategy);

    Result<Void> update(ScenicStrategy scenicStrategy);

    Result<Void> batchDelete(List<Integer> ids);

    Result<List<ScenicStrategyVO>> query(ScenicStrategyQueryDto dto);

    Result<Void> audit(Integer id);

    Result<List<ScenicStrategyListVO>> queryList(ScenicStrategyQueryDto dto);


}
