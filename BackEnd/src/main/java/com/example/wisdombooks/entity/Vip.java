package com.example.wisdombooks.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Vip implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String idCard;
    private String uName;
    private Date buyTime;
    private Date vipTimeout;

    public Vip() {
    }

    public Vip(String idCard, String uName, Date vipTimeout) {
        this.idCard = idCard;
        this.uName = uName;
        this.vipTimeout = vipTimeout;
    }
}
