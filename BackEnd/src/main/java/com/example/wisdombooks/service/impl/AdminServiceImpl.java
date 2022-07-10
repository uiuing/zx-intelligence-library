package com.example.wisdombooks.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.wisdombooks.entity.Admin;
import com.example.wisdombooks.mapper.AdminMapper;
import com.example.wisdombooks.service.AdminService;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
}
