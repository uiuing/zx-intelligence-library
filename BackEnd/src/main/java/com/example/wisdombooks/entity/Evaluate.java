package com.example.wisdombooks.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
public class Evaluate implements Serializable {

    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String bNo;
    private String studentName;
    private String studentId;
    private String pic;
    private Date evaluateTime;
    private String content;
    @TableField("parentId")
    private Long parentId;
    private Integer stars;

    // 回复评论
    @TableField(exist = false)
    private List<Evaluate> replyEvaluates = new ArrayList<>();
    @TableField(exist = false)
    private Evaluate parentEvaluate;
    @TableField(exist = false)
    private String parentStudentName;
}
