package cn.kmbeast.redis;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RedisPageCache<T> {
    private List<T> records;
    private Integer total;
}
