package com.xingye.service.impl;

import com.xingye.mapper.AdminMapper;
import com.xingye.pojo.Admin;
import com.xingye.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Override
    public Admin login(String username, String password) {
        return adminMapper.findByUsernameAndPassword(username, password);
    }
}
