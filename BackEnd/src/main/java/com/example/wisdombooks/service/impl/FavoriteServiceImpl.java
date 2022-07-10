package com.example.wisdombooks.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wisdombooks.entity.Favorite;
import com.example.wisdombooks.mapper.FavoriteMapper;
import com.example.wisdombooks.service.FavoriteService;
import org.springframework.stereotype.Service;

@Service
public class FavoriteServiceImpl extends ServiceImpl<FavoriteMapper, Favorite> implements FavoriteService {
}
