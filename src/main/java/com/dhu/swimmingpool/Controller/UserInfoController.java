package com.dhu.swimmingpool.Controller;

import com.dhu.swimmingpool.Pojo.Result;
import com.dhu.swimmingpool.Pojo.User;
import com.dhu.swimmingpool.Service.UserService;
import com.dhu.swimmingpool.Util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserService userService;

    //返回单个用户的信息
    @PostMapping("/")
    public Result getUserInfo() {
        Result payLoad = ThreadUtil.Thread_Local_get();
        Map<String, Object> data = (Map<String, Object>) payLoad.getData();
        System.out.println(data);
        Long uid = Long.parseLong(data.get("uid").toString());
        Map<String, Object> userInfo = userService.getUserInfo(uid);
        if (userInfo.isEmpty() || userInfo == null) {
            return Result.error("获取用户信息失败");
        } else {
            return Result.success(userInfo);
        }
    }

    @PostMapping("/getIdByUsername")
    public Result getIdByUsername(@RequestParam String username) {
        Long idByUserName = userService.getIdByUserName(username);
        if (idByUserName != null) {
            return Result.success(idByUserName);
        } else {
            return Result.error("未找到ID");
        }
    }

    @PostMapping("/updateUserInfo")
    public Result updateUserInfo(@RequestBody User user) {
        boolean res = userService.updateUserInfo(user);
        if (res) {
            return Result.success();
        } else {
            return Result.error("更新失败");
        }
    }

    @PostMapping("/getUserCount")
    public Result getUserCount() {
        return Result.success(userService.getUserNumber());
    }

    @PostMapping("/getMemberCount")
    public Result getMemberCount() {
        return Result.success(userService.getMemberNumber());
    }
    @PostMapping("/matchUsername")
    public Result matchUserNameWithPrefix(String prefix){
        return Result.success(userService.matchUsername(prefix));
    }
    @PostMapping("/getSomeUserInfoByUsername")
    public Result getSomeUserInfoByUsername (String username){
        return Result.success(userService.getSomeUserInfoByUsername(username));
    }
}
