package com.example.wisdombooks.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wisdombooks.entity.Book;
import com.example.wisdombooks.mapper.BookMapper;
import com.example.wisdombooks.service.BookService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class BookServiceImpl extends ServiceImpl<BookMapper, Book> implements BookService {
    @Override
    public List<Map<String, Object>> listWithTree() {
        // 查询出所有分类
        QueryWrapper<Book> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("categorys,count(categorys) as cateCnt")
                .groupBy("categorys");
        List<Map<String, Object>> maps = this.listMaps(queryWrapper);
        return maps;
    }
}
