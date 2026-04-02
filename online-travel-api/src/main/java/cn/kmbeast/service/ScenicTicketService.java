package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicTicketQueryDto;
import cn.kmbeast.pojo.entity.ScenicTicket;
import cn.kmbeast.pojo.vo.ScenicTicketVO;

import java.util.List;

public interface ScenicTicketService {

    Result<Void> save(ScenicTicket scenicTicket);

    Result<Void> update(ScenicTicket scenicTicket);

    Result<Void> batchDelete(List<Integer> ids);

    Result<List<ScenicTicketVO>> query(ScenicTicketQueryDto dto);

    Result<List<ScenicTicketVO>> queryScenicTicket();

}
