package cn.kmbeast.service.impl;

import cn.kmbeast.context.LocalThreadHolder;
import cn.kmbeast.mapper.HotelMapper;
import cn.kmbeast.mapper.VendorMapper;
import cn.kmbeast.pojo.api.ApiResult;
import cn.kmbeast.pojo.api.Result;
import cn.kmbeast.pojo.dto.query.extend.HotelQueryDto;
import cn.kmbeast.pojo.dto.query.extend.VendorQueryDto;
import cn.kmbeast.pojo.entity.Hotel;
import cn.kmbeast.pojo.vo.HotelVO;
import cn.kmbeast.pojo.vo.VendorVO;
import cn.kmbeast.service.HotelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 酒店的业务逻辑实现类
 */
@Service
public class HotelServiceImpl implements HotelService {

    @Resource
    private HotelMapper hotelMapper;
    @Resource
    private VendorMapper vendorMapper;

    /**
     * 酒店新增
     *
     * @param hotel 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> save(Hotel hotel) {
        // 设置初始时间
        hotel.setCreateTime(LocalDateTime.now());
        hotel.setVendorId(getVendorId());
        hotelMapper.save(hotel);
        return ApiResult.success();
    }

    /**
     * 酒店修改
     *
     * @param hotel 实体
     * @return Result<Void>
     */
    @Override
    public Result<Void> update(Hotel hotel) {
        hotelMapper.update(hotel);
        return ApiResult.success();
    }

    /**
     * 酒店删除
     *
     * @param ids 酒店ID列表
     * @return Result<Void>
     */
    @Override
    public Result<Void> batchDelete(List<Integer> ids) {
        hotelMapper.batchDelete(ids);
        return ApiResult.success();
    }

    /**
     * 酒店查询
     *
     * @param dto 查询实体
     * @return Result<HotelVO>
     */
    @Override
    public Result<List<HotelVO>> query(HotelQueryDto dto) {
        Integer totalCount = hotelMapper.queryCount(dto);
        List<HotelVO> result = hotelMapper.query(dto);
        return ApiResult.success(result, totalCount);
    }

    /**
     * 查询供应商名下维护的酒店信息
     *
     * @return Result<Void>
     */
    @Override
    public Result<List<HotelVO>> queryHotelVendor(HotelQueryDto dto) {
        Integer vendorId = getVendorId();
        dto.setVendorId(vendorId);
        return query(dto);
    }

    /**
     * 获取供应商的ID
     *
     * @return Integer
     */
    private Integer getVendorId() {
        VendorQueryDto vendorQueryDto = new VendorQueryDto();
        // 由用户ID去查回来的供应商信息
        vendorQueryDto.setUserId(LocalThreadHolder.getUserId());
        List<VendorVO> vendorVOS = vendorMapper.query(vendorQueryDto);
        // 1. 要么真的没有 2. 有的话，只有一项
        if (vendorVOS.isEmpty()) {
            return null;
        }
        return vendorVOS.get(0).getId();
    }
}
