package com.example.wisdombooks.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wisdombooks.common.BaseContext;
import com.example.wisdombooks.common.R;
import com.example.wisdombooks.entity.Borrowed;
import com.example.wisdombooks.entity.CommentAndUser;
import com.example.wisdombooks.entity.Evaluate;
import com.example.wisdombooks.entity.Student;
import com.example.wisdombooks.service.BorrowedService;
import com.example.wisdombooks.service.CommentAndUserService;
import com.example.wisdombooks.service.EvaluateService;
import com.example.wisdombooks.service.StudentService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/evaluate")
public class EvaluateController {
    @Autowired
    private EvaluateService evaluateService;
    @Autowired
    private StudentService studentService;
    @Autowired
    private BorrowedService borrowedService;
    @Autowired
    private CommentAndUserService commentAndUserService;

    /**
     * 添加评论
     *
     * @param evaluate
     * @return
     */
    @PutMapping
    public R<String> add(@RequestBody Evaluate evaluate) {
        // 借阅后才有评论权限
        Borrowed borrowed = borrowedService.one(BaseContext.getCurrentId(), evaluate.getBNo());
        if (borrowed == null) {
            return R.error("借阅后才能评价哦~");
        }
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getIdCard, BaseContext.getCurrentId());
        Student one = studentService.getOne(wrapper);
        evaluate.setEvaluateTime(new Date(System.currentTimeMillis()));
        evaluate.setPic(one.getPic());
        evaluateService.save(evaluate);
        return R.success("评论成功!");
    }

    /**
     * 获取用户评论列表
     *
     * @return
     */
    @GetMapping
    public R<List<Evaluate>> list() {
        List<Evaluate> evaluates = evaluateService.listEvaluate();
        return R.success(evaluates);
    }

    /**
     * 获取书籍下的评论列表
     *
     * @return
     */
    @GetMapping("/page")
    public R<Page<Evaluate>> page(String bno, int page, int pageSize) {
        LambdaQueryWrapper<Evaluate> wrapper = new LambdaQueryWrapper<>();
        Page<Evaluate> pageInfo = new Page<>(page, pageSize);
        wrapper.eq(Evaluate::getBNo, bno)
                .eq(Evaluate::getParentId, Long.parseLong("-1"));
        evaluateService.page(pageInfo, wrapper);
        return R.success(pageInfo);
    }

    /**
     * 删除评论
     *
     * @param id
     * @return
     */
    @DeleteMapping
    public R<String> del(Long id) {
        LambdaQueryWrapper<Evaluate> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Evaluate::getParentId, id);
        evaluateService.remove(wrapper);
        boolean b = evaluateService.removeById(id);
        if (b) {
            return R.success("删除成功!");
        }
        return R.error("删除失败!");
    }

    /**
     * 查询是否已经点赞
     *
     * @param id
     * @return
     */
    @GetMapping("/getLike")
    public R<String> getLike(Long id) {
        // 查询是否已经点赞
        LambdaQueryWrapper<CommentAndUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentAndUser::getCommentId, id)
                .eq(CommentAndUser::getUserId, BaseContext.getCurrentId());
        CommentAndUser one = commentAndUserService.getOne(queryWrapper);
        if (one != null) {
            return R.error("您已经点赞过了!");
        }
        return R.success("还未点赞!");
    }

    /**
     * 点赞评论
     *
     * @param id
     * @return
     */
    @GetMapping("/like")
    public R<String> like(Long id) {
        Evaluate evaluate = evaluateService.getById(id);
        LambdaUpdateWrapper<Evaluate> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Evaluate::getId, id)
                .set(Evaluate::getStars, evaluate.getStars() + 1);
        boolean b = evaluateService.update(wrapper);
        if (!b) {
            return R.error("点赞失败!");
        }
        commentAndUserService.save(new CommentAndUser(id, BaseContext.getCurrentId()));
        return R.success("点赞成功!");
    }


    /**
     * 取消点赞
     *
     * @param id
     * @return
     */
    @GetMapping("/unlike")
    public R<String> unlike(Long id) {
        Evaluate evaluate = evaluateService.getById(id);
        LambdaUpdateWrapper<Evaluate> wrapper = new LambdaUpdateWrapper<>();
        wrapper.eq(Evaluate::getId, id)
                .set(Evaluate::getStars, evaluate.getStars() - 1);
        boolean b = evaluateService.update(wrapper);
        if (!b) {
            return R.error("取消点赞失败!");
        }
        LambdaQueryWrapper<CommentAndUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(CommentAndUser::getCommentId,id)
                .eq(CommentAndUser::getUserId,BaseContext.getCurrentId());
        return R.success("取消点赞成功!");
    }

    /**
     * 获取评论点赞列表
     *
     * @return
     */
    @GetMapping("/getLikeList")
    public R<Page<CommentAndUser>> getLikeList(Long commentId) {
        Page<CommentAndUser> page = new Page<>(1, 50);
        commentAndUserService.page(page);
        return R.success(page);
    }
}
