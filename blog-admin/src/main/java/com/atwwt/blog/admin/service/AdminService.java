package com.atwwt.blog.admin.service;

import com.atwwt.blog.admin.mapper.AdminMapper;
import com.atwwt.blog.admin.pojo.Admin;
import com.atwwt.blog.admin.pojo.Permission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminService {
    @Autowired
    private AdminMapper adminMapper;
    public Admin findAdminByUserName(String username){
        LambdaQueryWrapper<Admin> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Admin::getUsername,username);
        queryWrapper.last("limit 1");
        Admin admin = adminMapper.selectOne(queryWrapper);
        return admin;
    }

    public List<Permission> findPermissionByAdmin(Long id) {

        return adminMapper.findPermissionByAdminId(id);
    }
}
