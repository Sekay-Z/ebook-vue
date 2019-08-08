package com.shukai.ebook.service;

import com.shukai.ebook.exception.UserException;

public interface MailService {
    public String sendMail(String account) throws UserException;
}
