package com.example.wisdombooks.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wisdombooks.entity.Borrowed;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface BorrowedMapper extends BaseMapper<Borrowed> {

    @Select("select * from borrowed_list where id_card = #{idCard} and b_no = #{bNo}")
    Borrowed one(@Param("idCard") String idCard,@Param("bNo") String bNo);
}
