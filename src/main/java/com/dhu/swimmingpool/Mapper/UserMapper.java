package com.dhu.swimmingpool.Mapper;

import com.dhu.swimmingpool.Pojo.SysUserLogin;
import com.dhu.swimmingpool.Pojo.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Map;

@Transactional
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

    @Select("select sys_user_login.UID      as UID,\n" +
        "       sys_user_login.UserName as UserName,\n" +
        "       UserState               as UserState,\n" +
        "       sys_user_login.RID\n" +
        "                               as UROLEID,\n" +
        "       UNAME                   as NickName,\n" +
        "       UPHONE                  as UPHONE,\n" +
        "       UIDENTITY               as UIdentity,\n" +
        "       UAVATAR                 as UAvatar,\n" +
        "       UDescription            as\n" +
        "                                  UDescripton,\n" +
        "       Description             as RDescription,\n" +
        "       CreateTime\n" +
        "from sys_user_login,\n" +
        "     user,\n" +
        "     role\n" +
        "where role.RID = sys_user_login.RID\n" +
        "  and user.UID = sys_user_login.UID" +
        "  and user.UID = #{Uid}")
    Map<String, Object> getUserInfoById(Long Uid);

    @Select("select * from role_auth,auth where role_auth.AID = auth.AID and role_auth.RID = " +
        "#{rid}")
    ArrayList<Map<String, Object>> getRolePath(Long rid);

    @Select("select sys_user_login.UID from sys_user_login where UserName=#{username}")
    Long getIdByUserName(String username);

    @Update("update sys_user_login set RID = #{rid} where UID = #{uid} ;")
    public boolean setRole(Long rid, Long uid);


    public boolean updateUserInfo(User user);

    @Select("select count(*) from sys_user_login where RID=3 or RID = 4")
    public Long getUserNumber();
    @Select("select count(*) from sys_user_login where RID = 4")
    public Long getMemberNumber();

    @Select("select sys_user_login.UserName from sys_user_login where UserName like CONCAT" +
        "(#{prefix},'%') limit 5")
    public ArrayList<String> matchUserName(String prefix);

    @Select("select user.UID,user.UNAME,user.UPHONE,user.UIDENTITY FROM user,sys_user_login WHERE" +
        " USER.UID = sys_user_login.UID AND sys_user_login.UserName = #{username}")
    public  Map<String,Object> getSomeInfoByUsername(String username);
}
