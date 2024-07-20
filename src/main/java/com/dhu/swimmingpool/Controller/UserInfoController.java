package com.dhu.swimmingpool.Controller;

import com.dhu.swimmingpool.Pojo.Result;
import com.dhu.swimmingpool.Service.UserService;
import com.dhu.swimmingpool.Util.ThreadUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/userInfo")
public class UserInfoController {
    @Autowired
    private UserService userService;

    //返回单个用户的信息
    @PostMapping("/")
    public Result getUserInfo() {
        String token = ThreadUtil.Thread_Local_get();
        Map<String, Object> userInfo = userService.getUserInfo(token);
        if(userInfo.isEmpty()||userInfo==null){
            return Result.error("获取用户信息失败");
        }else{
            return Result.success(userInfo);
        }
    }

}
