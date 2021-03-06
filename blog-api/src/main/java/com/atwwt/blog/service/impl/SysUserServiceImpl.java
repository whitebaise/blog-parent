package com.atwwt.blog.service.impl;

import com.atwwt.blog.dao.mapper.SysUserMapper;
import com.atwwt.blog.dao.pojo.SysUser;
import com.atwwt.blog.service.LoginService;
import com.atwwt.blog.service.SysUserService;
import com.atwwt.blog.vo.ErrorCode;
import com.atwwt.blog.vo.LoginUserVo;
import com.atwwt.blog.vo.Result;
import com.atwwt.blog.vo.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
public class SysUserServiceImpl implements SysUserService {
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private RedisTemplate<String,String> redisTemplate;
    @Autowired
    private LoginService loginService;

    @Override
    public UserVo findUserVoById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser==null){
            sysUser = new SysUser();
            sysUser.setId(1L);
            sysUser.setAvatar("/static/img/logo.b3a48c0.png");
            sysUser.setNickname("wwt");
        }
        UserVo userVo=new UserVo();
        BeanUtils.copyProperties(sysUser,userVo);
        return userVo;

    }

    @Override
    public SysUser findUserById(Long id) {
        SysUser sysUser = sysUserMapper.selectById(id);
        if (sysUser==null){
            sysUser=new SysUser();
            sysUser.setNickname("wwt");
        }
        return sysUser;

    }

    @Override
    public SysUser findUser(String account, String password) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.eq(SysUser::getPassword,password);
        queryWrapper.select(SysUser::getAccount,SysUser::getId,SysUser::getAvatar,SysUser::getNickname);
        queryWrapper.last("limit 1");
        return sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public Result findUserByToken(String token) {
        /**
         * 1. token???????????????
         *    ????????????????????????????????? redis????????????
         * 2. ?????????????????? ????????????
         * 3. ???????????????????????????????????? LoginUserVo
         */
        SysUser sysUser = loginService.checkToken(token);
        if (sysUser == null){
            return Result.fail(ErrorCode.TOKEN_ERROR.getCode(),ErrorCode.TOKEN_ERROR.getMsg());
        }
        LoginUserVo loginUserVo = new LoginUserVo();
        loginUserVo.setId(sysUser.getId());
        loginUserVo.setNickname(sysUser.getNickname());
        loginUserVo.setAvatar(sysUser.getAvatar());
        loginUserVo.setAccount(sysUser.getAccount());
        return Result.success(loginUserVo);
    }

    @Override
    public SysUser findUserByAccount(String account) {
        LambdaQueryWrapper<SysUser> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysUser::getAccount,account);
        queryWrapper.last("limit 1");
        this.sysUserMapper.selectOne(queryWrapper);
        return this.sysUserMapper.selectOne(queryWrapper);
    }

    @Override
    public void save(SysUser sysUser) {
        //?????? ???????????????id ????????????id ?????????????????????
        this.sysUserMapper.insert(sysUser);
    }
}
