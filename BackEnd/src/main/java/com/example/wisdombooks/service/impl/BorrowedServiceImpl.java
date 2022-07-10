package com.example.wisdombooks.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wisdombooks.entity.Borrowed;
import com.example.wisdombooks.mapper.BorrowedMapper;
import com.example.wisdombooks.service.BorrowedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BorrowedServiceImpl extends ServiceImpl<BorrowedMapper, Borrowed> implements BorrowedService {
    @Autowired
    private BorrowedMapper borrowedMapper;

    @Override
    public Borrowed one(String idCard,String bNo) {
        return borrowedMapper.one(idCard, bNo);
    }
}
