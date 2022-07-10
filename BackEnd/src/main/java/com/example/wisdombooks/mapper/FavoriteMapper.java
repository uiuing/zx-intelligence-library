package com.example.wisdombooks.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wisdombooks.entity.BookCnt;
import com.example.wisdombooks.entity.Favorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

@Mapper
public interface FavoriteMapper extends BaseMapper<Favorite> {

}
