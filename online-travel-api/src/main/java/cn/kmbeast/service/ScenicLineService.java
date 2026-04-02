package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicLineQueryDto;
import cn.kmbeast.pojo.entity.ScenicLine;
import cn.kmbeast.pojo.vo.ScenicLineVO;

import java.util.List;

public interface ScenicLineService {

    Result<Void> save(ScenicLine scenicLine);

    Result<Void> update(ScenicLine scenicLine);

    Result<Void> batchDelete(List<Integer> ids);

    Result<List<ScenicLineVO>> query(ScenicLineQueryDto dto);

}
