package com.shukai.ebook.controller;

import com.aliyuncs.exceptions.ClientException;
import com.shukai.ebook.bean.User;
import com.shukai.ebook.service.UserServiceImpl;
import com.shukai.ebook.util.Result;
import com.shukai.ebook.util.SmsUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;

    @GetMapping("/getCode")
    public Result getCode(@RequestParam("phone") String phone, HttpSession session){
        if(userServiceImpl.getUserByAccount(phone)!=null){
            Result.getInstance().setMessage("该手机号已被注册!");
            return Result.getInstance();
        }else{
            String code= null;
            try {
                code = SmsUtil.sendSMS(phone);
                if(code.equals("发送失败!")) {
                    Result.getInstance().setMessage("发送失败!");
                    return Result.getInstance();
                }
                else{
                    session.setAttribute("code",code);
                    session.setAttribute("phone",phone);
                    Result.getInstance().setMessage("发送成功!");
                    return Result.getInstance();
                }
            }catch (ClientException e) {
                e.printStackTrace();
                Result.getInstance().setMessage("发送失败!");
                return Result.getInstance();
            }
        }
    }
    @PostMapping("/regist")
    public Result regist(@RequestBody User user, HttpSession session){
        String code= (String) session.getAttribute("code");
        String phone= (String) session.getAttribute("phone");
        if(code!=null &&
                !"".equals(code)&&
                code.equals(user.getCode())&&
                phone.equals(user.getAccount())){
            if(userServiceImpl.getUserByMail(user.getMail())!=null){
                Result.getInstance().setMessage("邮箱已被注册!");
                return Result.getInstance();
            }
            else{
                userServiceImpl.addUser(user);
                Result.getInstance().setMessage("注册成功!");
                return Result.getInstance();
            }
        }else{
            Result.getInstance().setMessage("验证码错误!");
            return Result.getInstance();
        }
    }

   @GetMapping(value = "/login",produces = {"application/json;charset=UTF-8"})
   public String  login(){
      return  "请先登录哦 亲!";
   }

    @GetMapping("/getAllUser")
    public List<User> getAllUser(){
        return userServiceImpl.getAllUser();
    }

    @GetMapping("/changeAllowed")
    public Result changeAllowed(@RequestParam("account") String account){
        int row=userServiceImpl.changeAllowed(account);
        if(row>0){
            Result.getInstance().setMessage("修改成功!");
            Result.getInstance().setObject(null);
            return Result.getInstance();
        }else{
            Result.getInstance().setMessage("修改失败!");
            Result.getInstance().setObject(null);
            return Result.getInstance();
        }
    }

    @GetMapping("/init")
    public Result init(HttpSession session){
       User user= (User) session.getAttribute("user");
       if(user==null){
          Result.getInstance().setObject(null);
          Result.getInstance().setMessage(null);
           return Result.getInstance();
       }
       Result.getInstance().setObject(user);
       Result.getInstance().setMessage(null);
       return Result.getInstance();
    }
}
