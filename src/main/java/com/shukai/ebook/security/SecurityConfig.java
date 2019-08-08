package com.shukai.ebook.security;

import com.shukai.ebook.security.MyUserDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsUtils;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailService myUserDetailService;
    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;
    @Autowired
    private MyLoginSuccessHandler myLoginSuccessHandler;
    @Autowired
    private MyLoginFailureHandler myLoginFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailService).passwordEncoder(new BCryptPasswordEncoder());
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/book/getBooks","/book/searchBook","/book/getBookByISBN","/user/regist",
                        "/mail/sendMail","/user/getCode","/src/downloadFile/*").permitAll()
                .antMatchers("/book/addBook").hasAuthority("ADD_BOOK")
                .antMatchers("/book/deleteBook").hasAuthority("DELETE_BOOK")
                .antMatchers("/book/modifyBook").hasAuthority("MODIFY_BOOK")
                .antMatchers("/user/getAllUser").hasAuthority("GET_ALL_USER")
                .antMatchers("/user/changeAllowed").hasAuthority("CHANGE_ALLOWED")
                .antMatchers("/order/findAllUserOrder").hasAuthority("FIND_ALL_USER_ORDER")
                .antMatchers("/order/searchOrder").hasAuthority("SEARCH_ORDER")
                .antMatchers("/order/finish").hasAuthority("FINISH_ORDER")
                .anyRequest().authenticated()
                .and()
                .formLogin().loginPage("/user/login").permitAll()
                .and()
                .logout().logoutUrl("/logout").logoutSuccessHandler(myLogoutSuccessHandler).permitAll();
        http.cors().and().csrf().disable();
        http.addFilterAt(CAFilter(), UsernamePasswordAuthenticationFilter.class);
    }
    @Bean
    CustomAuthenticationFilter CAFilter() throws Exception {
        CustomAuthenticationFilter filter = new CustomAuthenticationFilter();
        filter.setAuthenticationSuccessHandler(myLoginSuccessHandler);
        filter.setAuthenticationFailureHandler(myLoginFailureHandler);
        filter.setAuthenticationManager(authenticationManagerBean());
        return filter;
    }
}
