package com.shukai.ebook.security;

import com.shukai.ebook.bean.Permission;
import com.shukai.ebook.bean.SecurityUser;
import com.shukai.ebook.bean.User;
import com.shukai.ebook.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user=userService.getUserByAccount(username);
        List<Permission> permList=userService.getPermissionByName(username);
        List<GrantedAuthority> list=new ArrayList<>();

        SecurityUser securityUser=new SecurityUser();
        if(user==null){
            securityUser.setUsername("wrong");
            securityUser.setPassword("wrong");
        }else{
            securityUser.setUsername(username);
            securityUser.setPassword(new BCryptPasswordEncoder().encode(user.getPassword()));
            for(Permission permission:permList){
                list.add(new SimpleGrantedAuthority(permission.getPerm_tag()));
            }
            securityUser.setAuthorities(list);
        }
        return securityUser;
    }
}
