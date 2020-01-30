package com.room.config;

import com.room.entity.User;
import com.room.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@EnableWebSecurity
@Configuration
public class MyWebSecuriteConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private MyUserDetailsService myUserDetailsService;
    @Autowired
    private UserMapper userMapper;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.authorizeRequests()
                .antMatchers("/register", "/update", "/bootstrap/**", "/js/**", "/css/**", "/rooms/**", "/fonts/**", "/images/**").permitAll()
                .antMatchers("/**").hasAnyRole("USER")
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/go").permitAll()
                .loginProcessingUrl("/login")
                .successHandler((req, resp, auth) -> {
                    //获取登录者信息
                    Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
                    if (principal != null && principal instanceof UserDetails) {
                        UserDetails user = (UserDetails) principal;
                        User myUser = userMapper.select(user.getUsername());
                        myUser.setPassword("");
//                        System.out.println("login success:"+user.getUsername());
//                        System.out.println(user);
                        //维护在session中
                        req.getSession().setAttribute("sessionUser", myUser);
                        resp.sendRedirect("/suc");
                    }
                })
//                .failureHandler((req,resp,authException)->{
//                    System.out.println(authException.getMessage());
//                    System.out.println("NONONO");
//                    resp.sendRedirect("/fal");
//                })
                .permitAll()
                .and()
                .logout().logoutUrl("/logout")
                .logoutSuccessUrl("/go")
                .permitAll();

        http.csrf().disable()
                .headers().frameOptions().sameOrigin();

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(myUserDetailsService).passwordEncoder(new BCryptPasswordEncoder());
    }
}
