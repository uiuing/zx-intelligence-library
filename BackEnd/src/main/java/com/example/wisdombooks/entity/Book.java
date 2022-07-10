package com.example.wisdombooks.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
public class Book implements Serializable {
    // 书籍编号
    @TableId(type = IdType.ASSIGN_ID)
    private String bNo;
    // 书名
    @NotBlank(message = "书名不能为空")
    private String bookName;
    // 作者笔名
    @NotBlank(message = "作者笔名不能为空")
    private String authorPenname;
    // 书籍分类
    @NotBlank(message = "书籍分类不能为空")
    private String categorys;
    // 简介
    @NotBlank(message = "简介不能为空")
    private String descs;
    // 图片名
    @NotBlank(message = "图片名不能为空")
    private String pic;
    // 图书库存总量
    @NotBlank(message = "库存总量不能为空")
    private Integer total;
    // 书籍库存
    @NotBlank(message = "书籍库存不能为空")
    private Integer nums;
    // 图书存储位置
    @NotBlank(message = "图书存储位置不能为空")
    private String bookAddress;
    // 是否可租
    @NotBlank(message = "是否可租不能为空")
    private Integer status;
}
