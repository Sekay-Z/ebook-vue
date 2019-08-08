package com.shukai.ebook.service;

import com.shukai.ebook.bean.Permission;
import com.shukai.ebook.bean.User;
import com.shukai.ebook.dao.UserDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public void addUser(User user) {
        userDao.addUser(user);
        userDao.addUserRole(user);
    }

    @Override
    public User getUserByAccount(String account) {
        return userDao.getUserByAccount(account);
    }

    @Override
    public User getUserByMail(String mail) {
        return userDao.getUserByMail(mail);
    }

    @Override
    public List<User> getAllUser() {
        return userDao.getAllUser();
    }

    @Override
    public int changeAllowed(String account) {
        return userDao.changeAllowed(account);
    }

    @Override
    public List<Permission> getPermissionByName(String name) {
        return userDao.getPermissionByName(name);
    }
}
