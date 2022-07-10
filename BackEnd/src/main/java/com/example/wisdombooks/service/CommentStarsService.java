package com.example.wisdombooks.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.wisdombooks.entity.CommentStars;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CommentStarsService extends IService<CommentStars> {
    /**
     * 批量保存
     *
     * @param list
     * @return
     */
    List<CommentStars> savaAll(List<CommentStars> list);

    /**
     * 根据被评论的id查询点赞列表
     *
     * @param commentId
     * @param pageable
     * @return
     */
    Page<CommentStars> getLikedListByLikedByCommendId(String commentId, Page<CommentStars> page);

    /**
     * 根据被用户id查询点赞列表
     *
     * @param stuId
     * @param page
     * @return
     */
    Page<CommentStars> getLikedListByLikedStuId(String stuId, Page<CommentStars> page);

    CommentStars getByLikedUserIdAndLikedPostId(Long commentId, String stuId);

    /**
     * 将redis里面的点赞数据存入数据库
     */
    void transLikedFromRedis2DB();

    /**
     * 将redis中的点赞数量数据存入数据库
     */
    void transLikedCountFromRedis2DB();
}
