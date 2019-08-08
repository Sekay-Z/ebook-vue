package com.shukai.ebook.service;

import com.shukai.ebook.bean.User;
import com.shukai.ebook.exception.UserException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender javaMailSender;
    @Autowired
    private UserServiceImpl userService;

    @Override
    public String sendMail(String account) throws UserException {
        User user=userService.getUserByAccount(account);
        if(user==null){
            throw new UserException("用户不存在!");
        }
        String password=user.getPassword();
        String mail=user.getMail();
        SimpleMailMessage simpleMailMessage=new SimpleMailMessage();
        simpleMailMessage.setFrom("1491558763@qq.com");
        simpleMailMessage.setTo(mail);
        simpleMailMessage.setSubject("密码找回");
        simpleMailMessage.setText("你的密码为："+password);
        try{
            javaMailSender.send(simpleMailMessage);
        }catch(Exception e){
            throw new UserException("发送失败!");
        }
      return mail;
    }
}
