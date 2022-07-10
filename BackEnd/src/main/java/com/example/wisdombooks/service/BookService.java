package com.example.wisdombooks.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wisdombooks.entity.Book;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface BookService extends IService<Book> {
    List<Map<String, Object>> listWithTree();
}
