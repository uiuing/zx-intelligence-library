package com.example.wisdombooks.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Student implements Serializable {
    // 学号
    private String studentId;
    // 姓名
    private String studentName;
    // 头像链接
    private String pic;
    // 身份证号
    private String idCard;
    // 登录密码
    @TableId(value = "passWd")
    private String passWd;
    // 所属学院
    private String college;
    // 专业
    private String specialty;
    // 入学日期
    private String madmissionDate;
    // 毕业日期
    private String graduationDate;
    // 入学状态
    private String status;
    // 余额
    private Double balance;
    // 超时次数
    private Integer timeOutCnt;
    // 是否会员
    private Integer ifVip;
    // 会员过期时间
    private Date vipTimeout;
    // 是否可借
    private Integer ifFree;
}
