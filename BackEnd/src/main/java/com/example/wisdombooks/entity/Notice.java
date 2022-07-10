package com.example.wisdombooks.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Notice implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String uId;
    private String uName;
    private String title;
    private String content;
    private Date createTime;
    private Date updateTime;
    private int sendStatus;
}
