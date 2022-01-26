package com.atwwt.blog.admin.mapper;

import com.atwwt.blog.admin.pojo.Admin;
import com.atwwt.blog.admin.pojo.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

public interface AdminMapper extends BaseMapper<Admin> {
    @Select("SELECT * FROM ms_permission where\n" +
            "id in(select permission_id from ms_admin_permission where admin_id=#{id});")
    List<Permission> findPermissionByAdminId(Long id);
}
