package com.example.wisdombooks.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wisdombooks.entity.Vip;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface VipMapper extends BaseMapper<Vip> {

    @Select("select date(buy_time) as day,count(1) as cnt from vip where DATE_SUB(CURDATE(), INTERVAL #{i} DAY) <= " +
            "date" +
            "(buy_time) group by " +
            "date" +
            "(buy_time)")
    Map<String, Long> timeRange(int i);
}
