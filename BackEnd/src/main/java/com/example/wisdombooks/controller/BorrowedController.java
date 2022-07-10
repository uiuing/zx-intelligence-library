package com.example.wisdombooks.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.wisdombooks.common.BaseContext;
import com.example.wisdombooks.common.R;
import com.example.wisdombooks.dto.TimeoutNoticeDto;
import com.example.wisdombooks.entity.Book;
import com.example.wisdombooks.entity.BookCnt;
import com.example.wisdombooks.entity.Borrowed;
import com.example.wisdombooks.entity.Student;
import com.example.wisdombooks.service.BookCntService;
import com.example.wisdombooks.service.BookService;
import com.example.wisdombooks.service.BorrowedService;
import com.example.wisdombooks.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/borrowed")
public class BorrowedController {

    @Autowired
    public BorrowedService borrowedService;
    @Autowired
    public BookService bookService;
    @Autowired
    public StudentService studentService;
    @Autowired
    public BookCntService bookCntService;

    /**
     * 借阅书籍
     *
     * @param borrowed
     * @return
     */
    @PostMapping("/add")
    public R<String> add(@RequestBody Borrowed borrowed) {
        log.info(borrowed.toString());
        // 验证是否有租借权限
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getIdCard, BaseContext.getCurrentId());
        Student student = studentService.getOne(studentLambdaQueryWrapper);
        if (student.getIfFree() == 0) {
            return R.error("您没有租借权限!");
        }

        LambdaQueryWrapper<Borrowed> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Borrowed::getIdCard, borrowed.getIdCard());
        List<Borrowed> borrowedList = borrowedService.list(queryWrapper);
        // 验证是否会员，非会员只能同时租借3本
        if (student.getIfVip() == 0 && borrowedList.size() >= 3) {
            return R.error("您的租借数量已到达上限!");
        }
        queryWrapper.eq(Borrowed::getBNo, borrowed.getBNo());
        Borrowed one = borrowedService.getOne(queryWrapper);
        if (one != null) {
            return R.error("已经借过了哦~");
        }

        borrowedService.save(borrowed);
        LambdaQueryWrapper<Book> bookLambdaQueryWrapper = new LambdaQueryWrapper<>();
        bookLambdaQueryWrapper.eq(Book::getBNo, borrowed.getBNo());
        Book book = bookService.getOne(bookLambdaQueryWrapper);
        Integer nums = book.getNums(); // 获取书籍库库存数
        UpdateWrapper<Book> updateWrapper = new UpdateWrapper<>();
        if (nums - 1 == 0) {
            return R.error("这本书已经没有库存了哦~");
        }
        updateWrapper.eq("b_no", book.getBNo())
                .set("nums", nums - 1);
        bookService.update(updateWrapper);

        BookCnt bookCnt = bookCntService.One(book.getBNo());
        if (bookCnt == null) {
            bookCntService.save(new BookCnt(book.getBNo(), book.getBookName(), 1, 0));
        } else {
            Integer borrowedCnt = bookCnt.getBorrowedCnt();
            UpdateWrapper<BookCnt> bookCntUpdateWrapper = new UpdateWrapper<>();
            bookCntUpdateWrapper.eq("b_no", bookCnt.getBNo())
                    .set("borrowed_cnt", borrowedCnt + 1);
            bookCntService.update(bookCntUpdateWrapper);
        }
        return R.success("借阅成功!");
    }

    /**
     * 查看借阅列表
     *
     * @return
     */
    @GetMapping
    public R<List<Borrowed>> list() {
        log.info("查看借阅书籍列表");
        LambdaQueryWrapper<Borrowed> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Borrowed::getIdCard, BaseContext.getCurrentId());
        List<Borrowed> borrowedList = borrowedService.list(queryWrapper);
        return R.success(borrowedList);
    }

    /**
     * 还书
     *
     * @param bno
     * @return
     */
    @PutMapping
    public R<String> update(String bno) {
        log.info(bno + "===");
        LambdaUpdateWrapper<Borrowed> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Borrowed::getIdCard, BaseContext.getCurrentId())
                .eq(Borrowed::getBNo, bno)
                .set(Borrowed::getStatus, 1);
        borrowedService.update(wrapper);
        Integer nums = bookService.getOne(new LambdaQueryWrapper<Book>()
                .eq(Book::getBNo, bno)).getNums();
        LambdaUpdateWrapper<Book> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper.eq(Book::getBNo, bno)
                .set(Book::getNums, nums + 1);
        bookService.update(updateWrapper);
        return R.success("还书成功!");
    }

    /**
     * 检测是否超时
     *
     * @return
     */
    @GetMapping("/ifTimeout")
    public R<List<TimeoutNoticeDto>> timeoutDetection() {
        LambdaQueryWrapper<Borrowed> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Borrowed::getIdCard, BaseContext.getCurrentId());

        List<Borrowed> borrowedList = borrowedService.list(queryWrapper);
        List<TimeoutNoticeDto> list = new ArrayList<>();

        for (Borrowed borrowed : borrowedList) {
            long ts = borrowed.getEndDate().getTime() - System.currentTimeMillis();
            System.out.println(ts + "========");
            if (ts >= 0) {
                list.add(new TimeoutNoticeDto(borrowed.getBookName(), new Date(ts), "请注意归还时间哦!"));
            } else {
                LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
                lambdaQueryWrapper.eq(Student::getIdCard, BaseContext.getCurrentId());
                Student one = studentService.getOne(lambdaQueryWrapper);
                UpdateWrapper<Student> wrapper = new UpdateWrapper<>();
                wrapper.eq("id_card", BaseContext.getCurrentId())
                        .set("time_out_cnt", one.getTimeOutCnt() + 1);
                list.add(new TimeoutNoticeDto(borrowed.getBookName(), new Date(0), "已超时，尽快归还书籍!"));
            }
        }
        return R.success(list);
    }
}
