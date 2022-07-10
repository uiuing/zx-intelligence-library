package com.example.wisdombooks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wisdombooks.entity.BookCnt;

public interface BookCntService extends IService<BookCnt> {

    BookCnt One(String bNo);
}
