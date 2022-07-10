package com.example.wisdombooks.controller;

import com.example.wisdombooks.common.R;
import com.example.wisdombooks.service.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@CrossOrigin
@RestController
@RequestMapping("/api/comment")
public class CommentStarsController {
    @Autowired
    RedisService redisService;

    /**
     * 点赞
     *
     * @param commentId
     * @param stuId
     * @return
     */
    @PostMapping("/like")
    public R<String> like(Long commentId,String stuId) {
        log.info("{}点赞了{}", stuId, commentId);
        redisService.saveLiked2Redis(commentId, stuId);
        redisService.incrementLikedCount(commentId);
        return R.success("点赞成功");
    }


    /**
     * 取消点赞
     *
     * @param commentId
     * @param stuId
     * @return
     */
    @PostMapping("/unlike")
    public R<String> unlike(Long commentId, String stuId) {
        redisService.unlikeFromRedis(commentId, stuId);
        redisService.decrementLikedCount(commentId);
        return R.success("取消点赞成功");
    }


}
