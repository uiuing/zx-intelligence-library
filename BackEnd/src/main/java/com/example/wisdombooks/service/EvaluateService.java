package com.example.wisdombooks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wisdombooks.entity.Evaluate;

import java.util.List;

public interface EvaluateService extends IService<Evaluate> {
    // 查询用户评论列表
    List<Evaluate> listEvaluate();
    // 查询书籍评论列表
//    List<Evaluate> listEvaluate(String bno);
}
