package com.dhu.swimmingpool.Service;

import com.dhu.swimmingpool.Pojo.SysUserLogin;
import com.dhu.swimmingpool.Pojo.User;

import java.util.ArrayList;
import java.util.Map;

public interface UserService {
    public boolean register (String username,String password);
    public SysUserLogin login (String username,String password);
    public Map<String,Object> getUserInfo (Long uid);
    public ArrayList<Map<String,Object>> getPath(Long rid);
    public Long getIdByUserName(String username);
    public boolean updateUserRole (Long rid,Long uid);
    public boolean updateUserInfo (User user);
    public Long getUserNumber();
    public Long getMemberNumber();
    public ArrayList<String> matchUsername(String prefix);
    public Map<String,Object> getSomeUserInfoByUsername(String username);
}
