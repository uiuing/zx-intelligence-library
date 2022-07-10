package com.example.wisdombooks.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wisdombooks.entity.Recharge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Date;
import java.util.Map;

@Mapper
public interface RechargeMapper extends BaseMapper<Recharge> {
    @Select("select date(recharge_time) as day,sum(recharge_amount) as sum from recharge where DATE_SUB(CURDATE(), " +
            "INTERVAL " +
            "#{i} " +
            "DAY) <= date" +
            "(recharge_time) group by " +
            "date(recharge_time)")
    Map<String, Date> timeRange(int i);
}
