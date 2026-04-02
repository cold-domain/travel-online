package cn.kmbeast.pojo.dto.query.extend;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotelOrderInfoQueryParamDto {

    private List<Integer> hotelRoomIds;

    private HotelOrderInfoQueryDto hotelOrderInfoQueryDto;
}
