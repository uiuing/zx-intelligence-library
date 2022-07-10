package com.example.wisdombooks.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.wisdombooks.common.BaseContext;
import com.example.wisdombooks.common.R;
import com.example.wisdombooks.entity.Book;
import com.example.wisdombooks.entity.BookCnt;
import com.example.wisdombooks.entity.Favorite;
import com.example.wisdombooks.entity.Student;
import com.example.wisdombooks.service.BookCntService;
import com.example.wisdombooks.service.BookService;
import com.example.wisdombooks.service.FavoriteService;
import com.example.wisdombooks.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/favorite")
public class FavoriteController {

    @Autowired
    public FavoriteService favoriteService;
    @Autowired
    public StudentService studentService;
    @Autowired
    public BookService bookService;
    @Autowired
    public BookCntService bookCntService;

    /**
     * 单个添加
     *
     * @param request
     * @param bNo
     * @param bookName
     * @return
     */
    @PutMapping
    public R<String> add(HttpServletRequest request, String bNo, String bookName) {
        log.info("收藏{}->{}", bNo, bookName);
        // 判断是否是登录
        if (request.getSession().getAttribute("idCard") == null) {
            return R.error("异常访问!");
        }

        String currentId = BaseContext.getCurrentId();
        if (StringUtils.isEmpty(currentId) && StringUtils.isEmpty(bNo) && StringUtils.isEmpty(bookName)) {
            return R.error("添加错误!");
        }
        LambdaQueryWrapper<Favorite> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Favorite::getIdCard, BaseContext.getCurrentId()).eq(Favorite::getBNo, bNo);
        if (favoriteService.getOne(wrapper) != null) {
            return R.error("已经收藏过啦，不要重复操作哦!");
        }
        // 判断当前用户是不是会员，非会员只能收藏10本书
        LambdaQueryWrapper<Student> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Student::getIdCard, BaseContext.getCurrentId());
        Student student = studentService.getOne(queryWrapper);
        if (student.getIfVip() == 0) {
            List<Favorite> list = favoriteService.list();
            if ((long) list.size() >= 10) {
                return R.error("您的收藏额度已用尽，开通会员可以收藏更多哦!");
            }
        }
        Favorite favorite = new Favorite();
        favorite.setIdCard(currentId);
        favorite.setBNo(bNo);
        favorite.setBookName(bookName);
        favoriteService.save(favorite);

        BookCnt one = bookCntService.One(bNo);
        if (one == null) {
            bookCntService.save(new BookCnt(bNo, bookName, 0, 1));
        } else {
            Integer favoriteCnt = one.getFavoriteCnt();
            UpdateWrapper<BookCnt> bookCntUpdateWrapper = new UpdateWrapper<>();
            bookCntUpdateWrapper.eq("b_no", bNo)
                    .set("favorite_cnt", favoriteCnt + 1);
            bookCntService.update(bookCntUpdateWrapper);
        }

        return R.success("添加成功!");
    }

    @GetMapping
    public R<List<Favorite>> list(HttpServletRequest request) {
        log.info("获取收藏列表");
        // 判断是否是登录
        if (request.getSession().getAttribute("idCard") == null) {
            return R.error("异常访问!");
        }

        LambdaQueryWrapper<Favorite> favoriteLambdaQueryWrapper = new LambdaQueryWrapper<>();
        favoriteLambdaQueryWrapper.eq(Favorite::getIdCard, BaseContext.getCurrentId());
        List<Favorite> favoriteList = favoriteService.list(favoriteLambdaQueryWrapper);
        return R.success(favoriteList);
    }

    /**
     * 单个删除
     *
     * @param request
     * @param favorite
     * @return
     */
    @DeleteMapping
    public R<String> del(HttpServletRequest request, Favorite favorite) {
        log.info("删除{}->{}", favorite.getBNo(), favorite.getBookName());
        // 判断是否是登录
        if (request.getSession().getAttribute("idCard") == null) {
            return R.error("异常访问!");
        }
        LambdaQueryWrapper<Favorite> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Favorite::getIdCard, BaseContext.getCurrentId())
                .eq(Favorite::getBNo, favorite.getBNo());
        favoriteService.remove(lambdaQueryWrapper);
        return R.success("删除成功");
    }

    /**
     * 根据收藏的bNo获取书籍详情信息
     *
     * @param request
     * @param bNo
     * @return
     */
    @GetMapping("/to")
    public R<Book> to(HttpServletRequest request, String bNo) {
        log.info("进入{}详情页", bNo);
        // 判断是否是登录
        if (request.getSession().getAttribute("idCard") == null) {
            return R.error("异常访问!");
        }

        LambdaQueryWrapper<Book> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Book::getBNo, bNo);
        Book book = bookService.getOne(queryWrapper);
        return R.success(book);
    }
}
