package com.example.wisdombooks.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wisdombooks.entity.BookCnt;
import com.example.wisdombooks.mapper.BookCntMapper;
import com.example.wisdombooks.service.BookCntService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class BookCntServiceImpl extends ServiceImpl<BookCntMapper, BookCnt> implements BookCntService {
    @Autowired
    private BookCntMapper bookCntMapper;

    @Override
    public BookCnt One(String bNo) {
        return bookCntMapper.One(bNo);
    }
}
