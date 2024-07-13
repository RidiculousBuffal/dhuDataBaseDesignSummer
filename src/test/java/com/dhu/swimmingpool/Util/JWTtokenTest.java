package com.dhu.swimmingpool.Util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.*;
import com.dhu.swimmingpool.Pojo.SysUserLogin;
import org.junit.jupiter.api.Test;

import java.util.Map;

//import java.security.Key;

public class JWTtokenTest {
    public String createToken(SysUserLogin sysUserLogin) {
        byte[] key = "Zhougezuishuai22".getBytes();
        String token = JWT.create().setPayload(
            "User", sysUserLogin
        ).setIssuedAt(DateUtil.date()).setExpiresAt(DateUtil.nextWeek()).setKey(key).sign();
        return token;
    }

    @Test
    public void testToken() {
        SysUserLogin sysUserLogin = new SysUserLogin(
            1, "zlc", "123456", true, 1
        );
        System.out.println(createToken(sysUserLogin));
    }

    @Test
    public void verifyToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
            ".eyJVc2VyIjp7InVpZCI6MSwidXNlck5hbWUiOiJ6bGMiLCJ1c2VyUGFzc1dvcmQiOiIxMjM0NTYiLCJ1c2VyU3RhdGUiOnRydWUsInJpZCI6MX19.riXU_DvDsI_jpiouZ-h6G4EKKoBlg8kBMVQ1KMk2xW4";
        byte[] key = "Zhougezuishuai22".getBytes();
        boolean flag = JWT.of(token).setKey(key).verify();
        assert (flag);
    }

    @Test
    public void getPayLoadFromToken() {
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
            ".eyJVc2VyIjp7InVpZCI6MSwidXNlck5hbWUiOiJ6bGMiLCJ1c2VyUGFzc1dvcmQiOiIxMjM0NTYiLCJ1c2VyU3RhdGUiOnRydWUsInJpZCI6MX19.riXU_DvDsI_jpiouZ-h6G4EKKoBlg8kBMVQ1KMk2xW4";
        JWT jwt = JWT.of(token);
        Map<String, Object> obj = (Map<String, Object>) jwt.getPayload("User");
        System.out.println(obj.get("uid"));
    }
    @Test
    public void validateDate(){
        String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9" +
            ".eyJVc2VyIjp7InVpZCI6MSwidXNlck5hbWUiOiJ6bGMiLCJ1c2VyUGFzc1dvcmQiOiIxMjM0NTYiLCJ1c2VyU3RhdGUiOnRydWUsInJpZCI6MX0sImlhdCI6MTcyMDg2MjM3MiwiZXhwIjoxNzIxNDY3MTcyfQ.rcs4gB27WNWaDJlTPXRYQooe5XCMsDTM5R4EIYMLtyY";
        System.out.println(JWTValidator.of(token).validateDate(DateUtil.date()));
    }
}
