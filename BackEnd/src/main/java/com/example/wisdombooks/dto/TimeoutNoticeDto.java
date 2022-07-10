package com.example.wisdombooks.dto;

import lombok.Data;

import java.util.Date;

@Data
public class TimeoutNoticeDto {
    private String bookName;
    private Date time;
    private String msg;

    public TimeoutNoticeDto(String bookName, Date time, String msg) {
        this.bookName = bookName;
        this.time = time;
        this.msg = msg;
    }

    public TimeoutNoticeDto() {
    }
}
