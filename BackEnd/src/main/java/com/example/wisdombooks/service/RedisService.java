package com.example.wisdombooks.service;

import com.example.wisdombooks.dto.LikedDTO;
import com.example.wisdombooks.entity.CommentStars;

import java.util.List;

public interface RedisService {
    /**
     * 点赞，状态为1
     * @param likeCommentId
     * @param stuId
     */
    void saveLiked2Redis(Long likeCommentId,String stuId);

    /**
     * 取消点赞，将状态改变为0
     * @param likeCommentId
     * @param stuId
     */
    void unlikeFromRedis(Long likeCommentId,String stuId);

    /**
     * 从redis中删除一条点赞数据
     * @param likeCommentId
     * @param stuId
     */
    void deleteLikedFromRedis(Long likeCommentId,String stuId);

    /**
     * 将评论的点赞数量加1
     * @param likeCommentId
     */
    void incrementLikedCount(Long likeCommentId);


    /**
     * 将评论的点赞数量减1
     * @param likeCommentId
     */
    void decrementLikedCount(Long likeCommentId);

    /**
     * 获取redis中存储的所有点赞数据
     * @return
     */
    List<CommentStars> getLikedDataFromRedis();

    /**
     * 获取redis中存储的所有点赞数量
     * @return
     */
    List<LikedDTO> getLikedCountFromRedis();
}
