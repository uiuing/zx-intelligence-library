package com.example.wisdombooks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wisdombooks.entity.Borrowed;

public interface BorrowedService extends IService<Borrowed> {

    Borrowed one(String idCard, String bNo);
}
