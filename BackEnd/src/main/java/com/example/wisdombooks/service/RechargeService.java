package com.example.wisdombooks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wisdombooks.entity.Recharge;

import java.util.Date;
import java.util.Map;

public interface RechargeService extends IService<Recharge> {
    Map<String, Date> timeRange(int i);
}
