package com.dhu.swimmingpool.Interceptor;

import com.dhu.swimmingpool.Util.JwtUtil;
import com.dhu.swimmingpool.Util.ThreadUtil;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;
@Component
public class JWTInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("Authorization");
        response.setCharacterEncoding("utf8");
        request.setCharacterEncoding("utf8");
        //检查JWT是否有效以及过期
        if(JwtUtil.verifyToken(token).getCode()==0 && JwtUtil.validatedate(token).getCode()==0){
            ThreadUtil.THREAD_LOCAL_set(JwtUtil.getPayLoad(token));
            return true;
        }else{
            response.setStatus(401);
            response.setContentType("application/json;charset=UTF-8");
            PrintWriter printWriter = response.getWriter();
            Map<String,Object> map = new HashMap<>();
            map.put("code",401);
            map.put("message","token失效或错误");
            map.put("data",null);
            printWriter.write(new ObjectMapper().writeValueAsString(map));
            printWriter.flush();
            printWriter.close();
            return false;
        }

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        ThreadUtil.THREAD_LOCAL_remove();
    }
}
