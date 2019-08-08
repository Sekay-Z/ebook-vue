package com.shukai.ebook.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shukai.ebook.bean.SecurityUser;
import com.shukai.ebook.bean.User;
import com.shukai.ebook.service.UserServiceImpl;
import com.shukai.ebook.util.JsonUtil;
import com.shukai.ebook.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyLoginSuccessHandler implements AuthenticationSuccessHandler {
    @Autowired
    private UserServiceImpl userService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "http://localhost:8090");
        response.setHeader("Access-Control-Allow-Credentials","true");
        PrintWriter out=response.getWriter();
        SecurityUser securityUser = (SecurityUser)authentication.getPrincipal();
        User user = userService.getUserByAccount(securityUser.getUsername());
        if(user.getAllowed()){
            request.getSession().setAttribute("user",user);
            Result.getInstance().setMessage("登录成功!");
            Result.getInstance().setObject(user);
            out.write(JsonUtil.generate(Result.getInstance()));
            out.flush();
            out.close();
        }else{
            Result.getInstance().setMessage("用户被禁用!");
            Result.getInstance().setObject(null);
            out.write(JsonUtil.generate(Result.getInstance()));
            out.flush();
            out.close();
        }
    }
}
