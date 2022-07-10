package com.example.wisdombooks.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Recharge implements Serializable {
    @TableId(type = IdType.ASSIGN_ID)
    private Long id;
    private String idCard;
    private String uName;
    private double rechargeAmount; // 充值金额
    private Date rechargeTime;

    public Recharge() {
    }

    public Recharge(String idCard, String uName, double rechargeAmount) {
        this.idCard = idCard;
        this.uName = uName;
        this.rechargeAmount = rechargeAmount;
    }
}
