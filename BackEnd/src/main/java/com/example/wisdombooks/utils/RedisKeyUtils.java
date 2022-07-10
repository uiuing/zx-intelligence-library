package com.example.wisdombooks.utils;

public class RedisKeyUtils {
    // 保存评论点赞数据的key
    public static final String MAP_KEY_COMMENT_LIKED = "MAP_COMMENT_LIKED";
    // 保存评论被点赞数量的key
    public static final String MAP_KEY_COMMENT_LIKED_COUNT = "MAP_COMMENT_LIKED_COUNT";

    /**
     * 拼接被点赞的评论id和点赞的人的id作为key
     * @param commentId
     * @param stuId
     * @return
     */
    public static String getLikedKey(Long commentId, String stuId) {
        return commentId +
                "::" +
                stuId;
    }
}
