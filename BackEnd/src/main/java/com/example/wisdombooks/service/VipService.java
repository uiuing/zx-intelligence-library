package com.example.wisdombooks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wisdombooks.entity.Vip;

import java.util.Map;

public interface VipService extends IService<Vip> {
    Map<String, Long> timeRange(int i);
}
