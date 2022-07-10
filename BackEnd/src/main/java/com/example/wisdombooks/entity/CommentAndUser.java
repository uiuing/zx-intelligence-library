package com.example.wisdombooks.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class CommentAndUser implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private Long commentId;
    private String userId;

    public CommentAndUser() {
    }

    public CommentAndUser(Long commentId, String userId) {
        this.commentId = commentId;
        this.userId = userId;
    }
}
