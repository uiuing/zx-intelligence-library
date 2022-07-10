package com.example.wisdombooks.service.impl;

import com.example.wisdombooks.dto.LikedDTO;
import com.example.wisdombooks.entity.CommentStars;
import com.example.wisdombooks.enums.LikedStatusEnum;
import com.example.wisdombooks.service.RedisService;
import com.example.wisdombooks.utils.RedisKeyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class RedisServiceImpl implements RedisService {
    @Autowired
    RedisTemplate redisTemplate;

    // 点赞
    @Override
    public void saveLiked2Redis(Long likeCommentId, String stuId) {
        String key = RedisKeyUtils.getLikedKey(likeCommentId, stuId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, key, LikedStatusEnum.LIKE.getCode());
    }

    // 取消点赞
    @Override
    public void unlikeFromRedis(Long likeCommentId, String stuId) {
        String key = RedisKeyUtils.getLikedKey(likeCommentId, stuId);
        redisTemplate.opsForHash().put(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, key, LikedStatusEnum.UNLIKE.getCode());
    }

    // 从redis中删除一条点赞数据
    @Override
    public void deleteLikedFromRedis(Long likeCommentId, String stuId) {
        String key = RedisKeyUtils.getLikedKey(likeCommentId, stuId);
        redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, key);
    }

    // 将评论的点赞数量加1
    @Override
    public void incrementLikedCount(Long likeCommentId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_COMMENT_LIKED_COUNT, likeCommentId, 1);
    }

    // 将评论的点赞数量减1
    @Override
    public void decrementLikedCount(Long likeCommentId) {
        redisTemplate.opsForHash().increment(RedisKeyUtils.MAP_KEY_COMMENT_LIKED_COUNT, likeCommentId, -1);
    }

    // 获取redis中存储的所有点赞数据
    @Override
    public List<CommentStars> getLikedDataFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_COMMENT_LIKED,
                ScanOptions.NONE);
        List<CommentStars> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> entry = cursor.next();
            String key = (String) entry.getKey();
            // 分离出commentId stuId
            String[] split = key.split("::");
            Long commentId = Long.valueOf(split[0]);
            String stuId = split[1];
            Integer value = (Integer) entry.getValue();

            CommentStars commentStars = new CommentStars(commentId, stuId, value);
            list.add(commentStars);

            // 存入list后从redis中删除
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_COMMENT_LIKED, key);
        }
        return list;
    }

    // 获取redis中存储的所有点赞数量
    @Override
    public List<LikedDTO> getLikedCountFromRedis() {
        Cursor<Map.Entry<Object, Object>> cursor = redisTemplate.opsForHash().scan(RedisKeyUtils.MAP_KEY_COMMENT_LIKED_COUNT,
                ScanOptions.NONE);
        ArrayList<LikedDTO> list = new ArrayList<>();
        while (cursor.hasNext()) {
            Map.Entry<Object, Object> map = cursor.next();
            // 将点赞数量存储在LikedCountDT
            Long key = (Long) map.getKey();
            LikedDTO dto = new LikedDTO(key, (Integer) map.getValue());
            list.add(dto);
            // 从Redis中删除这条记录
            redisTemplate.opsForHash().delete(RedisKeyUtils.MAP_KEY_COMMENT_LIKED_COUNT, key);
        }
        return list;
    }
}
