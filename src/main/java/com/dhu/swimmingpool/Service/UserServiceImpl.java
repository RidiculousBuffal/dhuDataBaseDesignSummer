package com.dhu.swimmingpool.Service;

import com.dhu.swimmingpool.Mapper.UserMapper;
import com.dhu.swimmingpool.Pojo.SysUserLogin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.crypto.digest.DigestUtil;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserMapper userMapper;

    @Override
    public boolean register(String username, String password) {
        SysUserLogin user = userMapper.getUsersByUsername(username);
        if (user == null) {
            //允许注册
            if (userMapper.register(username, DigestUtil.md5Hex(password))) {
                //添加用户基本信息
                SysUserLogin regUser = userMapper.getUsersByUsername(username);
                Long uid = regUser.getUid();
                if (userMapper.insertBasicInfo(uid, username)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    @Override
    public SysUserLogin login(String username, String password) {
        //找到指定用户
        SysUserLogin usersByUsername = userMapper.getUsersByUsername(username);
        if(usersByUsername == null){
            return null;
        }else{
            if(usersByUsername.getUserPassWord().equals(DigestUtil.md5Hex(password))){
                return usersByUsername;
            }else{
                return null;
            }
        }
    }
}
