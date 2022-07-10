package com.example.wisdombooks.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.example.wisdombooks.enums.LikedStatusEnum;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class CommentStars implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private int id;
    private Long commentId;
    private String stuId;
    // 默认未点赞
    private int status = LikedStatusEnum.UNLIKE.getCode();

    public CommentStars(Long commentId, String stuId, int status) {
        this.commentId = commentId;
        this.stuId = stuId;
        this.status = status;
    }

    public CommentStars() {
    }

    private Date createTime;
    private Date updateTime;
}
