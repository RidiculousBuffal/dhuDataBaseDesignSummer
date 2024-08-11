package com.dhu.swimmingpool.Service;

import com.dhu.swimmingpool.Mapper.UserMapper;
import com.dhu.swimmingpool.Pojo.Result;
import com.dhu.swimmingpool.Pojo.SysUserLogin;
import com.dhu.swimmingpool.Pojo.User;
import com.dhu.swimmingpool.Util.JwtUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import cn.hutool.crypto.digest.DigestUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        if (usersByUsername == null) {
            return null;
        } else {
            if (usersByUsername.getUserPassWord().equals(DigestUtil.md5Hex(password))) {
                return usersByUsername;
            } else {
                return null;
            }
        }
    }


    @Override
    public Map<String, Object> getUserInfo(Long uid) {
        return userMapper.getUserInfoById(uid);
    }

    @Override
    public Long getIdByUserName(String username) {
        return userMapper.getIdByUserName(username);
    }

    @Override
    public ArrayList<Map<String, Object>> getPath(Long rid) {
        return userMapper.getRolePath(rid);
    }

    @Override
    public boolean updateUserRole(Long rid, Long uid) {
        return userMapper.setRole(rid, uid);
    }

    @Override
    public boolean updateUserInfo(User user) {
        return userMapper.updateUserInfo(user);
    }

    @Override
    public Long getUserNumber() {
        return userMapper.getUserNumber();
    }

    @Override
    public Long getMemberNumber() {
        return userMapper.getMemberNumber();
    }

    @Override
    public ArrayList<String> matchUsername(String prefix) {
        return userMapper.matchUserName(prefix);
    }

    @Override
    public Map<String, Object> getSomeUserInfoByUsername(String username) {
        return userMapper.getSomeInfoByUsername(username);
    }

    @Override
    public Map<String, Object> queryUserInfo(int pageNum, int PageSize, String username,
                                             String UName, Long rid,Integer state) {
        PageHelper.startPage(pageNum, PageSize);
        ArrayList<Map<String, Object>> queryUserInfo =
            userMapper.queryUserInfo(username, UName, rid,state);
        PageInfo page = new PageInfo(queryUserInfo);
        Map<String, Object> RES = new HashMap<String, Object>();
        RES.put("arr", queryUserInfo);
        RES.put("total", page.getTotal());
        return RES;
    }

    @Override
    public boolean blockUser(Long uid) {
        return userMapper.blockUser(uid);
    }

    @Override
    public ArrayList<String> getUNamebyPrefix(String prefix) {
        return userMapper.getUNamebyPrefix(prefix);
    }

    @Override
    public boolean resetPassword(String password,Long uid) {
        return userMapper.resetPassword(DigestUtil.md5Hex(password),uid);
    }

    @Override
    public SysUserLogin get_SYS_USER_LOGIN_by_id(Long uid) {
        return userMapper.getSYSUserLoginByUserID(uid);
    }

    @Override
    public boolean unblockUser(Long uid) {
        return userMapper.unblockUser(uid);
    }

    @Override
    public ArrayList<Map<String, Object>> getBlockedUser() {
        return userMapper.getBlockedUser();
    }
}
