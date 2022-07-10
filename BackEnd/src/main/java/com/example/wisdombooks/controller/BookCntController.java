package com.example.wisdombooks.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wisdombooks.common.R;
import com.example.wisdombooks.entity.BookCnt;
import com.example.wisdombooks.service.BookCntService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/cnt")
public class BookCntController {

    @Autowired
    private BookCntService bookCntService;

    /**
     * 按borrowed排序
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/borrowed")
    public R<Page<BookCnt>> listOrderByBorrowedCnt(Integer page, Integer pageSize) {
        Page<BookCnt> bookCntPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<BookCnt> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(BookCnt::getBorrowedCnt);
        bookCntService.page(bookCntPage, wrapper);

        return R.success(bookCntPage);
    }


    /**
     * 按favorite排序
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/favorite")
    public R<Page<BookCnt>> listOrderByFavoriteCnt(Integer page, Integer pageSize) {
        Page<BookCnt> bookCntPage = new Page<>(page, pageSize);
        LambdaQueryWrapper<BookCnt> wrapper = new LambdaQueryWrapper<>();
        wrapper.orderByDesc(BookCnt::getFavoriteCnt);
        bookCntService.page(bookCntPage, wrapper);
        return R.success(bookCntPage);
    }
}
