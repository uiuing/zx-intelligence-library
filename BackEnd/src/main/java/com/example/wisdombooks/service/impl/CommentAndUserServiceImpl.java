package com.example.wisdombooks.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wisdombooks.entity.CommentAndUser;
import com.example.wisdombooks.mapper.CommentAndUserMapper;
import com.example.wisdombooks.service.CommentAndUserService;
import org.springframework.stereotype.Service;

@Service
public class CommentAndUserServiceImpl extends ServiceImpl<CommentAndUserMapper, CommentAndUser> implements CommentAndUserService {
}
