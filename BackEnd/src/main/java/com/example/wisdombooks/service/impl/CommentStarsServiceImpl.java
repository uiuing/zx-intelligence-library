package com.example.wisdombooks.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wisdombooks.dto.LikedDTO;
import com.example.wisdombooks.entity.CommentStars;
import com.example.wisdombooks.enums.LikedStatusEnum;
import com.example.wisdombooks.mapper.CommentStarsMapper;
import com.example.wisdombooks.mapper.LikedCountDTOMapper;
import com.example.wisdombooks.service.CommentStarsService;
import com.example.wisdombooks.service.RedisService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CommentStarsServiceImpl extends ServiceImpl<CommentStarsMapper, CommentStars> implements CommentStarsService {

    @Autowired
    RedisService redisService;
    @Autowired
    CommentStarsMapper commentStarsMapper;
    @Autowired
    LikedCountDTOMapper likedCountDTOMapper;

    @Override
    @Transactional
    public List<CommentStars> savaAll(List<CommentStars> list) {
        list.forEach(item -> commentStarsMapper.save(item));
        return list;
    }

    @Override
    public Page<CommentStars> getLikedListByLikedByCommendId(String commentId, Page<CommentStars> page) {
        return commentStarsMapper.getLikedListByLikedCommentIdAndStatus(commentId, LikedStatusEnum.LIKE.getCode(), page);
    }

    @Override
    public Page<CommentStars> getLikedListByLikedStuId(String stuId, Page<CommentStars> page) {
        return commentStarsMapper.getLikedListByLikedStuId(stuId, LikedStatusEnum.LIKE.getCode(), page);
    }

    @Override
    public CommentStars getByLikedUserIdAndLikedPostId(Long commentId, String stuId) {
        return commentStarsMapper.getByLikedUserIdAndLikedPostId(commentId, stuId);
    }


    @Override
    @Transactional
    public void transLikedFromRedis2DB() {
        List<CommentStars> list = redisService.getLikedDataFromRedis();
        for (CommentStars commentStars : list) {
            CommentStars likedListByLikedStuId = getByLikedUserIdAndLikedPostId(commentStars.getCommentId(), commentStars.getStuId());
            if (likedListByLikedStuId == null) {
                // 没有记录，直接存入
                save(commentStars);
            } else {
                // 有记录，更新
                likedListByLikedStuId.setStatus(commentStars.getStatus());
                save(likedListByLikedStuId);
            }
        }
    }

    @Override
    @Transactional
    public void transLikedCountFromRedis2DB() {
        List<LikedDTO> list = redisService.getLikedCountFromRedis();
        for (LikedDTO dto : list) {
            LikedDTO likedCountDTO = likedCountDTOMapper.selectById(dto.getCommentId());
            if (likedCountDTO != null) {
                Integer nums = likedCountDTO.getCount() + dto.getCount();
                likedCountDTO.setCount(nums);
                // 更新点赞数量
                likedCountDTOMapper.updateById(likedCountDTO);
            }
        }
    }
}
