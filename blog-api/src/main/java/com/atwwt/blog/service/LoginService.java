package com.atwwt.blog.service;

import com.atwwt.blog.dao.pojo.SysUser;
import com.atwwt.blog.vo.Result;
import com.atwwt.blog.vo.params.LoginParam;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface LoginService {
    //登录功能
    Result login(LoginParam loginParam);

    SysUser checkToken(String token);
    //退出登录
    Result logout(String token);
    //注册
    Result register(LoginParam loginParam);
}
