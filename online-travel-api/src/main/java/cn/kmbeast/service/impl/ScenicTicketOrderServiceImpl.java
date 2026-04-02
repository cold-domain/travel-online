package cn.kmbeast.service.impl;

import cn.kmbeast.constant.RedisKeyConstants;
import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.mapper.ScenicMapper;
import cn.kmbeast.mapper.ScenicTicketMapper;
import cn.kmbeast.mapper.ScenicTicketOrderMapper;
import cn.kmbeast.mapper.VendorMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.base.QueryDto;
import cn.kmbeast.pojo.dto.query.extend.*;
import cn.kmbeast.pojo.entity.ScenicTicket;
import cn.kmbeast.pojo.entity.ScenicTicketOrder;
import cn.kmbeast.pojo.vo.*;
import cn.kmbeast.redis.RedisLockHelper;
import cn.kmbeast.service.ScenicTicketOrderService;
import cn.kmbeast.utils.DateUtil;
import cn.kmbeast.utils.DecimalUtils;
import cn.kmbeast.utils.MoneyUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 景区订单的业务逻辑实现类
 */
@Service
public class ScenicTicketOrderServiceImpl implements ScenicTicketOrderService {

    @Resource
    private ScenicTicketOrderMapper scenicTicketOrderMapper;
    @Resource
    private VendorMapper vendorMapper;
    @Resource
    private ScenicMapper scenicMapper;
    @Resource
    private ScenicTicketMapper scenicTicketMapper;
    @Resource
    private StringRedisTemplate stringRedisTemplate;
    @Resource
    private RedisLockHelper redisLockHelper;

    private static final DefaultRedisScript<Long> STOCK_DECREASE_SCRIPT = new DefaultRedisScript<Long>(
            "local stock = tonumber(redis.call('get', KEYS[1])); " +
                    "if not stock then return -1 end; " +
                    "local buy = tonumber(ARGV[1]); " +
                    "if stock < buy then return -2 end; " +
                    "return redis.call('decrby', KEYS[1], buy);",
            Long.class
    );

    /**
     * 景区订单新增
     *
     * @param scenicTicketOrder 实体
     * @return Result<Void>
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Result<Void> save(ScenicTicketOrder scenicTicketOrder) {
        if (!StringUtils.hasText(scenicTicketOrder.getConcatPerson()) ||
                !StringUtils.hasText(scenicTicketOrder.getConcatPhone())) {
            return ApiResult.error("联系人或联系电话不为空");
        }
        ScenicTicketQueryDto scenicTicketQueryDto = new ScenicTicketQueryDto();
        scenicTicketQueryDto.setId(scenicTicketOrder.getTicketId());
        List<ScenicTicketVO> scenicTicketVOS = scenicTicketMapper.query(scenicTicketQueryDto);
        if (scenicTicketVOS.isEmpty()) {
            return ApiResult.error("暂无门票信息");
        }
        ScenicTicketVO scenicTicketVO = scenicTicketVOS.get(0);
        Integer scenicId = scenicTicketVO.getScenicId();
        ScenicQueryDto scenicQueryDto = new ScenicQueryDto();
        scenicQueryDto.setId(scenicId);
        List<ScenicVO> scenicVOS = scenicMapper.query(scenicQueryDto);
        if (scenicVOS.isEmpty()){
            return ApiResult.error("景点异常");
        }
        // 景点是否可用，不可用就是维护情况
        if (!scenicVOS.get(0).getStatus()) {
            return ApiResult.error("景点暂停服务");
        }
        // 门票是否可用
        if (!scenicTicketVO.getUseStatus()) {
            return ApiResult.error("门票暂不可用");
        }
        String lockKey = RedisKeyConstants.scenicTicketOrderLockKey(scenicTicketVO.getId());
        String lockValue = UUID.randomUUID().toString();
        if (!redisLockHelper.tryLock(lockKey, lockValue, 10, TimeUnit.SECONDS)) {
            return ApiResult.error("当前下单人数较多，请稍后再试");
        }
        boolean stockReduced = false;
        try {
            String stockKey = RedisKeyConstants.scenicTicketStockKey(scenicTicketVO.getId());
            initTicketStock(stockKey, scenicTicketVO.getNumber());
            Long remainStock = stringRedisTemplate.execute(
                    STOCK_DECREASE_SCRIPT,
                    Collections.singletonList(stockKey),
                    String.valueOf(scenicTicketOrder.getBuyNumber())
            );
            if (remainStock == null || remainStock < 0) {
                return ApiResult.error("门票库存不足");
            }
            stockReduced = true;
            BigDecimal amount = DecimalUtils.calculateTotal(
                    scenicTicketOrder.getBuyNumber(),
                    scenicTicketVO.getPrice(),
                    scenicTicketVO.getDiscount() == null ? 1 : (scenicTicketVO.getDiscount() / 10)
            );
            scenicTicketOrder.setAmount(amount);
            scenicTicketOrder.setUserId(LocalThreadHolder.getUserId());
            scenicTicketOrder.setCreateTime(LocalDateTime.now());
            scenicTicketOrder.setTicketId(scenicTicketVO.getId());
            scenicTicketOrder.setPayStatus(false);
            scenicTicketOrderMapper.save(scenicTicketOrder);
            ScenicTicket scenicTicket = new ScenicTicket();
            scenicTicket.setId(scenicTicketVO.getId());
            scenicTicket.setNumber(remainStock.intValue());
            scenicTicketMapper.update(scenicTicket);
            stringRedisTemplate.delete(RedisKeyConstants.VIEWS_STATIC_CONTROLS_CACHE);
            return ApiResult.success("下单成功");
        } catch (RuntimeException ex) {
            if (stockReduced) {
                stringRedisTemplate.opsForValue().increment(
                        RedisKeyConstants.scenicTicketStockKey(scenicTicketVO.getId()),
                        scenicTicketOrder.getBuyNumber()
                );
            }
            throw ex;
        } finally {
            redisLockHelper.unlock(lockKey, lockValue);
        }
    }

    /**
     * 景区订单修改
     *
     * @param scenicTicketOrder 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> update(ScenicTicketOrder scenicTicketOrder) {
        scenicTicketOrderMapper.update(scenicTicketOrder);
        return ApiResult.success();
    }

    /**
     * 景区订单删除
     *
     * @param ids 景区订单ID列表
     * @return Result<Void>
     */
    @Override
    public Result<Void> batchDelete(List<Integer> ids) {
        scenicTicketOrderMapper.batchDelete(ids);
        stringRedisTemplate.delete(RedisKeyConstants.VIEWS_STATIC_CONTROLS_CACHE);
        return ApiResult.success();
    }

    /**
     * 景区订单查询
     *
     * @param dto 查询实体
     * @return Result<ScenicTicketOrderVO>
     */
    @Override
    public Result<List<ScenicTicketOrderVO>> query(ScenicTicketOrderQueryDto dto) {
        Integer totalCount = scenicTicketOrderMapper.queryCount(dto);
        List<ScenicTicketOrderVO> result = scenicTicketOrderMapper.query(dto);
        return ApiResult.success(result, totalCount);
    }

    /**
     * 查询供应商管理的全部景点的门票订单
     *
     * @return Result<Void>
     */
    @Override
    public Result<List<ScenicTicketOrderVO>> queryScenicTicketOrder(ScenicTicketOrderQueryDto dto) {
        // 取出供应商信息
        Integer vendorId = getVendorId();
        if (vendorId == null){
            return ApiResult.success(new ArrayList<>());
        }
        // 查供应商管理的全部的景点信息
        ScenicQueryDto queryDto = new ScenicQueryDto();
        queryDto.setVendorId(vendorId);
        List<ScenicVO> scenicVOS = scenicMapper.query(queryDto);
        // 取出里面的全部景点ID
        List<Integer> scenicIds = scenicVOS.stream()
                .map(ScenicVO::getId)
                .collect(Collectors.toList());
        // 用户ID --- 供应商ID --- 景点ID --- 门票ID --- 订单信息
        List<ScenicTicketVO> scenicTicketVOS
                = scenicTicketMapper.queryByScenicIds(scenicIds);
        // 获取门票的Id列表
        List<Integer> scenicTicketIds = scenicTicketVOS.stream()
                .map(ScenicTicketVO::getId)
                .collect(Collectors.toList());
        ScenicTicketQueryParamDto orderQueryParam = new ScenicTicketQueryParamDto(
                scenicTicketIds,
                dto
        );
        List<ScenicTicketOrderVO> scenicTicketOrderVOS =
                scenicTicketOrderMapper.queryByScenicIds(orderQueryParam);
        Integer totalCount =
                scenicTicketOrderMapper.queryCountByScenicIds(orderQueryParam);
        return ApiResult.success(scenicTicketOrderVOS, totalCount);
    }

    /**
     * 统计近期成交的金额
     *
     * @param day 往前面查的天数
     * @return Result<List < ChartVO>>
     */
    @Override
    public Result<List<ChartVO>> daysQuery(Integer day) {
        // 获取时间范围
        QueryDto queryDto = DateUtil.startAndEndTime(day);
        ScenicTicketOrderQueryDto dto = new ScenicTicketOrderQueryDto();
        dto.setStartTime(queryDto.getStartTime());
        dto.setEndTime(queryDto.getEndTime());
        Result<List<ScenicTicketOrderVO>> scenicTicketOrder
                = queryScenicTicketOrder(dto);
        ApiResult<List<ScenicTicketOrderVO>> response = (ApiResult) scenicTicketOrder;
        List<ScenicTicketOrderVO> data = response.getData();
        List<MoneyDto> moneyDtoList = data.stream().map(scenicTicketOrderVO -> new MoneyDto(
                scenicTicketOrderVO.getAmount(),
                scenicTicketOrderVO.getPayTime()
        )).collect(Collectors.toList());
        List<ChartVO> chartVOS = MoneyUtils.countMoney(day, moneyDtoList);
        return ApiResult.success(chartVOS);
    }

    /**
     * 查询用户自己的门票消费金额数据
     *
     * @param day 往前查多少天
     * @return Result<List < ChartVO>>
     */
    @Override
    public Result<List<ChartVO>> daysQueryUser(Integer day) {
        // 获取时间范围
        QueryDto queryDto = DateUtil.startAndEndTime(day);
        ScenicTicketOrderQueryDto dto = new ScenicTicketOrderQueryDto();
        dto.setStartTime(queryDto.getStartTime());
        dto.setEndTime(queryDto.getEndTime());
        // 设置上用户的ID
        dto.setUserId(LocalThreadHolder.getUserId());
        List<ScenicTicketOrderVO> scenicTicketOrderVOS = scenicTicketOrderMapper.query(dto);
        List<MoneyDto> moneyDtoList = scenicTicketOrderVOS.stream()
                .map(scenicTicketOrderVO -> new MoneyDto(
                        scenicTicketOrderVO.getAmount(),
                        scenicTicketOrderVO.getPayTime())).collect(Collectors.toList());
        List<ChartVO> chartVOS = MoneyUtils.countMoney(day, moneyDtoList);
        return ApiResult.success(chartVOS);
    }

    /**
     * 统计全站指定日期里面的成交门票金额
     *
     * @return Result<List < ChartVO>> 响应结果
     */
    @Override
    public Result<List<ChartVO>> daysQueryMoney(Integer day) {
        // 获取时间范围
        QueryDto queryDto = DateUtil.startAndEndTime(day);
        ScenicTicketOrderQueryDto dto = new ScenicTicketOrderQueryDto();
        dto.setStartTime(queryDto.getStartTime());
        dto.setEndTime(queryDto.getEndTime());
        List<ScenicTicketOrderVO> scenicTicketOrderVOS = scenicTicketOrderMapper.query(dto);
        List<MoneyDto> moneyDtoList = scenicTicketOrderVOS.stream()
                .map(scenicTicketOrderVO -> new MoneyDto(
                        scenicTicketOrderVO.getAmount(),
                        scenicTicketOrderVO.getPayTime())).collect(Collectors.toList());
        List<ChartVO> chartVOS = MoneyUtils.countMoney(day, moneyDtoList);
        return ApiResult.success(chartVOS);
    }

    /**
     * 取得供应商ID
     *
     * @return Integer
     */
    private Integer getVendorId() {
        VendorQueryDto vendorQueryDto = new VendorQueryDto();
        // 当前操作者的用户ID
        vendorQueryDto.setUserId(LocalThreadHolder.getUserId());
        List<VendorVO> vendorVOS = vendorMapper.query(vendorQueryDto);
        // 1. 要么真的没有 2. 有的话，只有一项
        if (CollectionUtils.isEmpty(vendorVOS)) {
            return null;
        }
        return vendorVOS.get(0).getId();
    }

    /**
     * 门票订单支付
     *
     * @param scenicTicketOrder 门票订单数据
     * @return Result<Void>
     */
    @Override
    public Result<Void> pay(ScenicTicketOrder scenicTicketOrder) {
        // 支付状态
        scenicTicketOrder.setPayStatus(true);
        // 支付事件
        scenicTicketOrder.setPayTime(LocalDateTime.now());
        scenicTicketOrderMapper.update(scenicTicketOrder);
        return ApiResult.success();
    }

    private void initTicketStock(String stockKey, Integer stock) {
        stringRedisTemplate.opsForValue().setIfAbsent(
                stockKey,
                String.valueOf(stock),
                7,
                TimeUnit.DAYS
        );
    }
}
