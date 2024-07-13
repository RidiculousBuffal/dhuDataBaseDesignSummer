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
}
