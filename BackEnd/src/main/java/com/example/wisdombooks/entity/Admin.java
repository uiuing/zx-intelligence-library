package com.example.wisdombooks.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

@Data
public class Admin implements Serializable {

    private String adminId;
    private String idCard;
    @TableId(value = "passWd")
    private String passWd;
    private String avatar;
}
