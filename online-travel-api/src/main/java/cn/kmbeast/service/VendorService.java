package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicCategoryQueryDto;
import cn.kmbeast.pojo.dto.query.extend.VendorQueryDto;
import cn.kmbeast.pojo.entity.ScenicCategory;
import cn.kmbeast.pojo.entity.Vendor;
import cn.kmbeast.pojo.vo.VendorVO;

import java.util.List;

public interface VendorService {

    Result<Void> save(Vendor vendor);

    Result<Void> update(Vendor vendor);

    Result<Void> batchDelete(List<Integer> ids);

    Result<List<VendorVO>> query(VendorQueryDto dto);

}
