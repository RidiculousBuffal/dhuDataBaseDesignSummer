package com.dhu.swimmingpool.Service;

import com.dhu.swimmingpool.Pojo.SysUserLogin;

import java.util.Map;

public interface UserService {
    public boolean register (String username,String password);
    public SysUserLogin login (String username,String password);
    public Map<String,Object> getUserInfo (String token);
}
