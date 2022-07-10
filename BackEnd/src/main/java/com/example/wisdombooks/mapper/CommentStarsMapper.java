package com.example.wisdombooks.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.wisdombooks.entity.CommentStars;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;


@Mapper
public interface CommentStarsMapper extends BaseMapper<CommentStars> {

    @Select("insert into comment_stars (comment_id,stu_id,status) values(#{commentId},#{stuId},#{status})")
    void save(@Param(value = "commentStars") CommentStars commentStars);

    @Select("select * from comment_stars where comment_id = #{commentId} and status = #{code}")
    Page<CommentStars> getLikedListByLikedCommentIdAndStatus(@Param(value = "commentId") String commentId,
                                                             @Param(value = "code") int code,
                                                             Page<CommentStars> page);

    @Select("select * from comment_stars where comment_id = #{commentId} and stu_id = #{stuId}")
    CommentStars getByLikedUserIdAndLikedPostId(@Param(value = "commentId") Long commentId,
                                                @Param(value = "stuId") String stuId);

    @Select("select * from comment_stars where stu_id = #{stuId} and status = #{code}")
    Page<CommentStars> getLikedListByLikedStuId(@Param(value = "stuId") String stuId,
                                                @Param(value = "code") int code,
                                                Page<CommentStars> page);
}
