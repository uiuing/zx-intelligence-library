package com.example.wisdombooks.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class BookCnt implements Serializable {
    private String bNo;
    private String bookName;
    private Integer borrowedCnt;
    private Integer favoriteCnt;

    public BookCnt() {
    }

    public BookCnt(String bNo, String bookName, Integer borrowedCnt, Integer favoriteCnt) {
        this.bNo = bNo;
        this.bookName = bookName;
        this.borrowedCnt = borrowedCnt;
        this.favoriteCnt = favoriteCnt;
    }
}
