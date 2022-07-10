package com.example.wisdombooks.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("favorite_list")
public class Favorite {
    // 身份证号
    private String idCard;
    // 书籍编号
    private String bNo;
    // 书名
    private String bookName;
}
