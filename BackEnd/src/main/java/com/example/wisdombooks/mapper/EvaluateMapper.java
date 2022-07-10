package com.example.wisdombooks.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.wisdombooks.entity.Evaluate;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface EvaluateMapper extends BaseMapper<Evaluate> {

    // 查询父级评论
    @Select("select * from evaluate e where e.parentId = #{parentId}")
    List<Evaluate> findByParentIdNull(@Param("parentId") Long parentId);

//    @Select("select * from evaluate e where e.parentId = -1 and  e.b_no = #{bno}")
//    List<Evaluate> findByParentIdNull(@Param("bno") String bno);

    //查询一级回复
    @Select("select * from evaluate e where e.parentId = #{id} order by e.evaluate_time desc")
    List<Evaluate> findParentIdNotNull(@Param("id") Long id);

    //查询二级以及所有子集回复
    @Select("select * from evaluate e where e.parentId = #{childId} order by e.evaluate_time desc")
    List<Evaluate> findByReplayId(@Param("childId") Long childId);
}
