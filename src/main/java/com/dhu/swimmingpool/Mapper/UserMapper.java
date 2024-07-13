package com.dhu.swimmingpool.Mapper;

import com.dhu.swimmingpool.Pojo.SysUserLogin;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

@Mapper
public interface UserMapper {
    @Insert("insert into sys_user_login(UserName, UserPassWord, UserState, RID) VALUES (" +
        "#{username},#{password},1,3)")
        // status 为1 表示账号状态为被启用  rid为3 表示普通用户
    boolean register(String username, String password);

    @Select("select * from sys_user_login where UserName = #{username}")
    SysUserLogin getUsersByUsername(String username);

    @Insert("insert into user (UID,UNAME) values (#{uid},#{name})")
    boolean insertBasicInfo(Long uid, String name);

}
