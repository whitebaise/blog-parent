package com.atwwt.blog.service;

import com.atwwt.blog.dao.pojo.SysUser;
import com.atwwt.blog.vo.Result;
import com.atwwt.blog.vo.UserVo;

public interface SysUserService {
    UserVo findUserVoById(Long id);

    SysUser findUserById(Long id);

    SysUser findUser(String account, String password);

    //根据token查询用户信息
    Result findUserByToken(String token);
    //根据账户查找用户
    SysUser findUserByAccount(String account);

    void save(SysUser sysUser);
}
