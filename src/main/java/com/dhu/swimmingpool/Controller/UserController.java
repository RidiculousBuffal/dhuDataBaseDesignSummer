package com.dhu.swimmingpool.Controller;

import com.dhu.swimmingpool.Pojo.Result;
import com.dhu.swimmingpool.Service.UserService;
import jakarta.validation.constraints.Pattern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

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
}
