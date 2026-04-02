package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicQueryDto;
import cn.kmbeast.pojo.entity.Scenic;
import cn.kmbeast.pojo.vo.ScenicVO;
import cn.kmbeast.pojo.vo.SelectedVO;

import java.util.List;

public interface ScenicService {

    Result<Void> save(Scenic scenic);

    Result<Void> update(Scenic scenic);

    Result<Void> batchDelete(List<Integer> ids);

    Result<List<ScenicVO>> query(ScenicQueryDto dto);

    Result<List<ScenicVO>> queryVendorScenic(ScenicQueryDto dto);

    Result<List<SelectedVO>> querySelectedScenic();

    Result<List<ScenicVO>> queryHot(Integer size);

    Result<Void> viewOperation(Integer scenicId);

    Result<Boolean> saveStatus(Integer scenicId);

    Result<Void> saveOperation(Integer scenicId);

    Result<List<ScenicVO>> querySave();


}
