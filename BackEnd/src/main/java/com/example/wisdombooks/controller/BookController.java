package com.example.wisdombooks.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wisdombooks.common.BaseContext;
import com.example.wisdombooks.common.R;
import com.example.wisdombooks.entity.Book;
import com.example.wisdombooks.service.BookService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.TimeUnit;


@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/book")
public class BookController {

    @Autowired
    private BookService bookService;
    @Autowired
    private RedisTemplate redisTemplate;

    /**
     * 书籍分类:分类下书籍数量
     *
     * @return
     */
    @GetMapping("/cate")
    public R<List<Map<String, Object>>> cate() {
        List<Map<String, Object>> listWithTree = bookService.listWithTree();
        return R.success(listWithTree);
    }

    /**
     * 书籍数据分页
     *
     * @param page
     * @param pageSize
     * @return
     */
    @GetMapping("/list")
    public R<Page<Book>> list(int page, int pageSize) {
        log.info("page = {}, pageSize = {}", page, pageSize);
        String key = "list_" + page + "_" + pageSize;
        Page<Book> pageInfo = null;
        pageInfo = (Page<Book>) redisTemplate.opsForValue().get(key);
        if (pageInfo != null) {
            return R.success(pageInfo);
        }

        pageInfo = new Page<>(page, pageSize);
        bookService.page(pageInfo);

        redisTemplate.opsForValue().set(key, pageInfo, 60, TimeUnit.MINUTES);
        return R.success(pageInfo);
    }

    /**
     * 根据书名模糊查询
     *
     * @param page
     * @param pageSize
     * @param name
     * @return
     */
    @GetMapping("/page")
    public R<Page<Book>> namePageList(int page, int pageSize, String name) {
        log.info("page = {}, pageSize = {}, name = {}", page, pageSize, name);
        // 构造分页构造器
        Page<Book> pageInfo = new Page<>(page, pageSize);
        // 构建查询条件构造器
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(name), Book::getBookName, name);
        // 执行查询
        bookService.page(pageInfo, queryWrapper);
        return R.success(pageInfo);
    }

    @GetMapping("/catePage")
    public R<Page<Book>> catePageList(int page, int pageSize, String cate) {
        log.info("page = {}, pageSize = {}, cate = {}", page, pageSize, cate);
        // 构造redis的key
        String key = "cate_" + cate + "_" + page + "_" + pageSize;
        Page<Book> pageInfo = null;
        // 先从redis中获取缓存数据
        pageInfo = (Page<Book>) redisTemplate.opsForValue().get(key);
        // 如果存在，直接返回
        if (pageInfo != null) {
            return R.success(pageInfo);
        }

        // 构造分页构造器
        pageInfo = new Page<>(page, pageSize);
        // 构建查询条件构造器
        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.like(StringUtils.isNotEmpty(cate), Book::getCategorys, cate);
        // 执行查询
        bookService.page(pageInfo, queryWrapper);
        // 如果redis中不存在，将查询出的结果保存至redis中
        redisTemplate.opsForValue().set(key, pageInfo, 60, TimeUnit.MINUTES);
        return R.success(pageInfo);
    }

    /**
     * 新增数据
     *
     * @param book
     * @return
     */
    @PostMapping
    public R<String> add(HttpServletRequest request, @RequestBody Book book) {
        log.info(book.toString());
        if (!Objects.equals(BaseContext.getCurrentId(), "111111111111111111")) {
            return R.error("非管理员不能操作!");
        }
        // 清理redis中缓存的书籍数据
        Set keys1 = redisTemplate.keys("list_*");
        redisTemplate.delete(keys1);
        Set keys2 = redisTemplate.keys("cate_*");
        redisTemplate.delete(keys2);

        boolean b = bookService.save(book);
        if (b) {
            return R.success("新增成功!");
        } else {
            return R.error("新增失败!");
        }
    }

    /**
     * 修改书籍
     *
     * @param book
     * @return
     */
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Book book) {
        log.info(book.toString());
        if (!Objects.equals(BaseContext.getCurrentId(), "111111111111111111")) {
            return R.error("非管理员不能操作!");
        }
        // 清理redis中缓存的书籍数据
        Set keys1 = redisTemplate.keys("list_*");
        redisTemplate.delete(keys1);
        Set keys2 = redisTemplate.keys("cate_*");
        redisTemplate.delete(keys2);

        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getBNo, book.getBNo());
        Book one = bookService.getOne(queryWrapper);
        if (one == null) {
            return R.success("书籍不存在!");
        }
        UpdateWrapper<Book> bookUpdateWrapper = new UpdateWrapper<>();
        bookUpdateWrapper.eq("b_no", book.getBNo())
                .set("nums", book.getNums())
                .set("book_address", book.getBookAddress())
                .set("status", book.getStatus());
        bookService.update(bookUpdateWrapper);
        return R.success("修改书籍成功!");
    }

    /**
     * 删除书籍
     *
     * @param request
     * @param bNo
     * @return
     */
    @DeleteMapping
    public R<String> del(HttpServletRequest request, String bNo) {
        log.info("删除编号为:{}的书籍", bNo);
        if (!Objects.equals(BaseContext.getCurrentId(), "111111111111111111")) {
            return R.error("非管理员不能操作!");
        }
        // 清理redis中缓存的书籍数据
        Set keys1 = redisTemplate.keys("list_*");
        redisTemplate.delete(keys1);
        Set keys2 = redisTemplate.keys("cate_*");
        redisTemplate.delete(keys2);

        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getBNo, bNo);
        boolean b = bookService.remove(queryWrapper);
        if (b) {
            return R.success("删除成功!");
        } else {
            return R.error("删除失败");
        }
    }
}
