package com.example.wisdombooks.dto;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

@Data
@TableName("liked_count_dto")
public class LikedDTO {
    private Long commentId;
    @TableId(value = "cnt")
    private Integer count;

    public LikedDTO(Long commentId, Integer count) {
        this.commentId = commentId;
        this.count = count;
    }
}
