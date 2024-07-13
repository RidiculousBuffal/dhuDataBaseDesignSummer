package com.dhu.swimmingpool.Util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.exceptions.ValidateException;
import cn.hutool.jwt.*;
import com.dhu.swimmingpool.Pojo.Result;
import com.dhu.swimmingpool.Pojo.SysUserLogin;

import java.util.Map;

public class JwtUtil {
    private static byte[] key = "Zhougezuishuai22".getBytes();

    public static String createToken(SysUserLogin sysUserLogin) {
        String token = JWT.create().setPayload("User", sysUserLogin).setIssuedAt(DateUtil.date()).setExpiresAt(DateUtil.nextWeek()).setKey(key).sign();
        return token;
    }
    public static Result verifyToken (String token){
        if(JWT.of(token).setKey(key).verify()){
            return Result.success();
        }else{
            return Result.error("token错误");
        }
    }
    public static Result getPayLoad(String token){
        Map<String,Object> payLoad = (Map<String,Object>)JWT.of(token).getPayload("User");
        if(!payLoad.isEmpty()){
            return Result.success(payLoad);
        }else{
            return Result.error("payLoad 为空");
        }
    }
    public static Result validatedate (String token){
        try{
            JWTValidator.of(token).validateDate(DateUtil.date());
            return Result.success();
        }catch (ValidateException v){
            return Result.error("token失效");
        }
    }
}
