package com.example.wisdombooks.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wisdombooks.entity.Vip;
import com.example.wisdombooks.mapper.VipMapper;
import com.example.wisdombooks.service.VipService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class VipServiceImpl extends ServiceImpl<VipMapper, Vip> implements VipService {
    @Autowired
    private VipMapper vipMapper;

    @Override
    public Map<String, Long> timeRange(int i) {
        return vipMapper.timeRange(i);
    }
}
