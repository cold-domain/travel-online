package cn.kmbeast.service;

import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.ScenicTicketOrderQueryDto;
import cn.kmbeast.pojo.entity.ScenicTicketOrder;
import cn.kmbeast.pojo.vo.ChartVO;
import cn.kmbeast.pojo.vo.ScenicTicketOrderVO;

import java.util.List;

public interface ScenicTicketOrderService {

    Result<Void> save(ScenicTicketOrder scenicTicketOrder);

    Result<Void> update(ScenicTicketOrder scenicTicketOrder);

    Result<Void> batchDelete(List<Integer> ids);

    Result<List<ScenicTicketOrderVO>> query(ScenicTicketOrderQueryDto dto);

    Result<List<ScenicTicketOrderVO>> queryScenicTicketOrder(ScenicTicketOrderQueryDto dto);

    Result<List<ChartVO>> daysQuery(Integer day);

    Result<Void> pay(ScenicTicketOrder scenicTicketOrder);


    Result<List<ChartVO>> daysQueryUser(Integer day);

    Result<List<ChartVO>> daysQueryMoney(Integer day);

}
