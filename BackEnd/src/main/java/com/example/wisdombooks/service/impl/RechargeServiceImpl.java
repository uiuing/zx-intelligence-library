package com.example.wisdombooks.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wisdombooks.entity.Recharge;
import com.example.wisdombooks.mapper.RechargeMapper;
import com.example.wisdombooks.service.RechargeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Map;

@Service
public class RechargeServiceImpl extends ServiceImpl<RechargeMapper, Recharge> implements RechargeService {

    @Autowired
    private RechargeMapper rechargeMapper;

    @Override
    public Map<String, Date> timeRange(int i) {
        return rechargeMapper.timeRange(i);
    }
}
