package com.shukai.ebook.security;

import com.shukai.ebook.util.JsonUtil;
import com.shukai.ebook.util.Result;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
public class MyLoginFailureHandler implements AuthenticationFailureHandler{

    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setContentType("application/json;charset=utf-8");
        response.setHeader("Access-Control-Allow-Origin", "http://20.27.242.6");
        response.setHeader("Access-Control-Allow-Credentials","true");
        PrintWriter out=response.getWriter();
        Result.getInstance().setMessage("用户名或密码错误");
        Result.getInstance().setObject(null);
        out.write(JsonUtil.generate(Result.getInstance()));
        out.flush();
        out.close();
    }
}
