package com.shukai.ebook.controller;

import com.shukai.ebook.exception.UserException;
import com.shukai.ebook.service.MailServiceImpl;
import com.shukai.ebook.util.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/mail")
public class MailController {
    @Autowired
    private MailServiceImpl mailService;
    @GetMapping("/sendMail")
    public Result sendMail(@RequestParam("account") String account){
        try {
            String mail=mailService.sendMail(account);
            Result.getInstance().setObject(mail);
            Result.getInstance().setMessage("发送成功!");
            return Result.getInstance();
        } catch (UserException e) {
            Result.getInstance().setMessage(e.getMessage());
            return Result.getInstance();
        }
    }
}
