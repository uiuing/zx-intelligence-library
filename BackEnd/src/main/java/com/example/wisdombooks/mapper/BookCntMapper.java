package com.example.wisdombooks.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wisdombooks.entity.BookCnt;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BookCntMapper extends BaseMapper<BookCnt> {
    @Select("select * from book_cnt where b_no = #{bNo}")
    BookCnt One(@Param("bNo") String bNo);
}
