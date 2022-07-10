package com.example.wisdombooks.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.wisdombooks.common.BaseContext;
import com.example.wisdombooks.common.R;
import com.example.wisdombooks.entity.Recharge;
import com.example.wisdombooks.entity.Student;
import com.example.wisdombooks.entity.Vip;
import com.example.wisdombooks.service.RechargeService;
import com.example.wisdombooks.service.StudentService;
import com.example.wisdombooks.service.VipService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.Date;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/user")
public class StudentController {

    @Autowired
    public StudentService studentService;
    @Autowired
    public VipService vipService;
    @Autowired
    public RechargeService rechargeService;

    /**
     * 返回个人信息
     * @param request
     * @return
     */
    @GetMapping
    public R<Student> get(HttpServletRequest request) {
        LambdaQueryWrapper<Student> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Student::getIdCard, request.getSession().getAttribute("idCard"));
        return R.success(studentService.getOne(wrapper));
    }

    /**
     * 登录
     *
     * @param request
     * @param student
     * @return
     */
    @PostMapping("/login")
    public R<Student> login(HttpServletRequest request, @RequestBody Student student) {
        log.info("学生账号:{}登录", student.getIdCard());
        // 根据用户提交的idCard查询数据库(用户名为唯一属性)
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Student::getIdCard, student.getIdCard());
        Student stu = studentService.getOne(lambdaQueryWrapper);
        // 没有查询到结果
        if (stu == null) {
            return R.error("登录失败!");
        }

        // 密码比对
        if (!stu.getPassWd().equals(student.getPassWd())) {
            return R.error("登录失败!");
        }

        // 登录成功，将idCard存入Session并返回登录成功结果
        request.getSession().setAttribute("idCard", stu.getIdCard());
        return R.success(stu);
    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request) {
        log.info("退出登录!");
        request.getSession().removeAttribute("idCard");
        BaseContext.remove();
        return R.success("退出成功!");
    }

    /**
     * 修改密码
     *
     * @param student
     * @return
     */
    @PutMapping("/changePassWd")
    public R<String> changePassWd(HttpServletRequest request, @RequestBody Student student) {
        log.info("学生账号:{}修改密码", student.getIdCard());
        // 判断是否是登录
        if (request.getSession().getAttribute("idCard") == null) {
            return R.error("异常访问!");
        }
        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Student::getIdCard, student.getIdCard());
        Student stu = studentService.getOne(lambdaQueryWrapper);
        if (stu == null) {
            return R.error("账号错误!");
        }

        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id_card", student.getIdCard()).set("passWd", student.getPassWd());
        studentService.update(updateWrapper);
        BaseContext.remove();
        request.getSession().removeAttribute("idCard");
        return R.success("修改密码成功，请重新登录!"); // 前端清除session
    }

    /**
     * 充值(伪)
     *
     * @param request
     * @param idCard
     * @param amount
     * @return
     */
    @PostMapping("/pay")
    public R<String> pay(HttpServletRequest request, String idCard, Double amount) {
        log.info("学生账号:{}充值{}", idCard, amount);
        // 判断是否是登录
        if (request.getSession().getAttribute("idCard") == null) {
            return R.error("异常访问");
        }

        LambdaQueryWrapper<Student> lambdaQueryWrapper = new LambdaQueryWrapper<>();
        lambdaQueryWrapper.eq(Student::getIdCard, idCard);
        Student one = studentService.getOne(lambdaQueryWrapper);
        if (one == null) {
            return R.error("账号错误!");
        }

        // 账户原余额
        Double balance = one.getBalance();
        System.out.println("余额为:" + balance);
        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id_card", one.getIdCard())
                .set("balance", balance + amount);
        studentService.update(updateWrapper);
        rechargeService.save(new Recharge(one.getIdCard(), one.getStudentName(), amount));
        return R.success("充值成功!");
    }

    /**
     * 购买vip
     *
     * @param request
     * @param idCard  购买的用户
     * @param n       购买多少个月
     * @return
     */
    @PostMapping("/vip")
    public R<String> vip(HttpServletRequest request, String idCard, Integer n) {
        log.info("学生账号:{}购买vip", idCard);
        // 判断是否是登录
        if (request.getSession().getAttribute("idCard") == null) {
            return R.error("异常访问");
        }
        LambdaQueryWrapper<Student> studentLambdaQueryWrapper = new LambdaQueryWrapper<>();
        studentLambdaQueryWrapper.eq(Student::getIdCard, idCard);
        Student one = studentService.getOne(studentLambdaQueryWrapper);
        if (one.getBalance() < n * 10) {
            return R.error("余额不足，购买失败!");
        }
        Date d = new Timestamp(System.currentTimeMillis());
        // 如果不是会员，就重新设置超时时间
        if (one.getIfVip() == 0) {
            one.setVipTimeout(d);
        }
        long ts = one.getVipTimeout().getTime() + n * 24 * 60 * 60 * 30 * 1000L;
        UpdateWrapper<Student> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id_card", one.getIdCard())
                .set("if_vip", 1)
                .set("vip_timeout", new Date(ts))
                .set("balance", one.getBalance() - n * 10);
        studentService.update(updateWrapper);
        vipService.save(new Vip(one.getIdCard(), one.getStudentName(), new Date(ts)));
        return R.success("购买成功!");
    }
}
