package com.example.wisdombooks.entity;


import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("borrowed_list")
public class Borrowed implements Serializable {
    // 身份证号
    @NotBlank(message = "身份证号不能为空")
    private String idCard;
    // 书籍编号
    @NotBlank(message = "书籍编号不能为空")
    private String bNo;
    // 书名
    @NotBlank(message = "书名不能为空")
    private String bookName;
    // 借书日期
    @NotBlank(message = "借书日期不能为空")
    private Date startDate;
    // 还书日期
    @NotBlank(message = "还书日期不能为空")
    private Date endDate;
    // 是否归还
    private Integer status;
}
