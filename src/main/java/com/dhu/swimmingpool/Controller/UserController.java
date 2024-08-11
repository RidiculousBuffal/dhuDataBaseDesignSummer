package com.dhu.swimmingpool.Controller;

import cn.hutool.crypto.digest.DigestUtil;
import com.dhu.swimmingpool.Pojo.Result;
import com.dhu.swimmingpool.Pojo.SysUserLogin;
import com.dhu.swimmingpool.Service.UserService;
import com.dhu.swimmingpool.Util.JwtUtil;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.dhu.swimmingpool.Util.ThreadUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@RestController
@CrossOrigin
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public Result register(@Pattern(regexp = "^\\S{5,16}$", message = "用户名要5-16位字符")
                           @RequestParam(required = true, value = "username") String username,
                           @Pattern(regexp = "^\\S{5,16}$", message = "密码要5-16位字符")
                           @RequestParam(required = true, value = "password") String password) {
        if (userService.register(username, password)) {
            return Result.success("注册成功");
        } else {
            return Result.error("注册失败");
        }
    }

    @PostMapping("/login")
    public Result login(@RequestBody Map<String, String> params) {
        SysUserLogin login = userService.login(params.get("username"), params.get("password"));
        if (login != null) {
            if (login.getUserState() != 1) {
                return Result.error("该账号已被停用");
            } else {
                return Result.success(JwtUtil.createToken(login));
            }
        } else {
            return Result.error("用户名或密码错误");
        }
    }

    @PostMapping("/path")
    public Result getPath() {
        Result payLoad = ThreadUtil.Thread_Local_get();
        Map<String, Object> data = (Map<String, Object>) payLoad.getData();
        System.out.println(data);
        Long rid = Long.parseLong(data.get("rid").toString());
        System.out.println("rid:" + rid);

        Map<String, Object> res = new HashMap<>();
        res.put("rid", rid);
        ArrayList<Map<String, Object>> path = userService.getPath(rid);
        res.put("path", path);
        System.out.println(res);
        return Result.success(res);
    }


    @PostMapping("/AdminAddUser")
    public Result addUser(String username, String password, Long rid) {
        Long idByUserName = userService.getIdByUserName(username);
        if (idByUserName != null) {
            return Result.error("用户已经存在");
        } else {
            //先注册
            userService.register(username, password) ;
            if ( userService.updateUserRole(rid, userService.getIdByUserName(username))) {
                return Result.success("添加用户成功!");
            } else {
                return Result.error("添加失败");
            }
        }
    }

    @PostMapping("/adminResetPassword")
    public Result adminResetPassword(String password, Long uid) {
        if (userService.resetPassword(password, uid)) {
            return Result.success();
        } else {
            return Result.error("错误");
        }
    }

    @PostMapping("/userResetPassword")
    public Result userResetPassword(String oldPassword, String newPassword, Long uid) {
        if (!Objects.equals(userService.get_SYS_USER_LOGIN_by_id(uid).getUserPassWord(), DigestUtil.md5Hex(oldPassword))) {
            return Result.error("旧密码错误");
        } else {
            if (userService.resetPassword(newPassword, uid)) {
                return Result.success("修改成功");
            } else {
                return Result.error("修改失败");
            }
        }
    }
    @PostMapping("/updateUserRole")
    public Result updateUserRole(Long rid,Long uid)
    {
        if(userService.updateUserRole(rid,uid)){
            return Result.success();
        }else{
            return Result.error("更新失败");
        }
    }
    @PostMapping("/updateUserState")
    public Result updateUserState(Boolean state,Long uid){
        if(state){
            if (userService.unblockUser(uid)) {
                return Result.success();
            }
        }else{
            if (userService.blockUser(uid)) {
                return Result.success();
            }
        }
       return Result.error("更新用户状态失败");
    }
    @PostMapping("/getBlockedUsers")
    public Result getBlockedUser(){
        return Result.success(userService.getBlockedUser());
    }
}
