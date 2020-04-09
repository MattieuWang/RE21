package com.re21.bouquiniste.configs;

import com.alibaba.fastjson.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.re21.bouquiniste.dao.UserMapper;
import com.re21.bouquiniste.modules.User;
import com.re21.bouquiniste.service.LoginService;
import com.re21.bouquiniste.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.boot.configurationprocessor.json.JSONObject;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.configurers.ExpressionUrlAuthorizationConfigurer;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

@Configuration
@Slf4j
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {

    @Resource
    private LoginService loginService;

    @Resource
    private UserService userService;

//    @Bean
//    public BCryptPasswordEncoder bCryptPasswordEncoder()
//    {
//        return new BCryptPasswordEncoder();
//    }

    @Bean
    public PasswordEncoder encoder()
    {
        return NoOpPasswordEncoder.getInstance();
    }

    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("admin").password("aaa").roles("admin");
        auth.userDetailsService(loginService);
    }

    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/")
                .authenticated()
                .antMatchers("/user/**")
                .hasAuthority("USER")
                .antMatchers("/users/**")
                .hasAuthority("ADMIN")
                .and()
                .formLogin()
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                .successHandler(new AuthenticationSuccessHandler() {
                    @Override
                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
                        log.debug("---------login success---------");
                        httpServletResponse.setContentType("application/json;charset=utf-8");
                        Object principal = authentication.getPrincipal();
                        PrintWriter out = httpServletResponse.getWriter();
                        httpServletResponse.setStatus(200);
                        Map<String,Object> map = new HashMap<>();
                        map.put("status",200);
                        map.put("msg",principal);
                        ObjectMapper objectMapper = new ObjectMapper();
                        System.out.println(objectMapper.writeValueAsString(map));
                        String username = authentication.getName();
                        User user = new User();
                        user.setUsername(username);
                        RequestCache cache = new HttpSessionRequestCache();
                        SavedRequest savedRequest = cache.getRequest(httpServletRequest,httpServletResponse);
                        System.out.println(user);
                        if (user.getUsername().equals("admin"))
                        {
                            map.put("msg","fail");
                            httpServletResponse.sendRedirect("/re21-v1/users");

                        }
                        user = userService.getUserByUsername(username);
                        httpServletRequest.getSession().setAttribute("userId",user.getUser_id());
                        httpServletResponse.sendRedirect("/re21-v1/user/"+user.getUser_id());
//                        out.write(objectMapper.writeValueAsString(map));
//                        out.flush();
//                        out.close();

                    }
                })
                .failureHandler(new AuthenticationFailureHandler() {
                    @Override
                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
                        log.debug("--------login fail---------");
                        PrintWriter out = httpServletResponse.getWriter();
                        httpServletResponse.setStatus(401);
                        Map<String,Object> map = new HashMap<>();
                        map.put("status",401);
                        map.put("msg","fail");
                        ObjectMapper objectMapper = new ObjectMapper();
                        out.write(objectMapper.writeValueAsString(map));
                        out.flush();
                        out.close();
                    }
                })
                .permitAll()
                .and()
                .httpBasic()
                .and()
                .csrf()
                .disable();
    }

//    protected void configure(HttpSecurity http) throws Exception {
//        http.authorizeRequests()
//                .antMatchers("/")
//                .hasAnyRole("user","admin")
//                .and()
//                .formLogin()
//                .loginProcessingUrl("/login")
//                .usernameParameter("username")
//                .passwordParameter("password")
//                .successHandler(new AuthenticationSuccessHandler() {
//                    @Override
//                    public void onAuthenticationSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
//                        log.debug("---------login success---------");
//                        httpServletResponse.setContentType("application/json;charset=utf-8");
//                        Object principal = authentication.getPrincipal();
//                        PrintWriter out = httpServletResponse.getWriter();
//                        httpServletResponse.setStatus(200);
//                        Map<String,Object> map = new HashMap<>();
//                        map.put("status",200);
//                        map.put("msg",principal);
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        System.out.println(objectMapper.writeValueAsString(map));
//                        User user = new User();
//                        try {
//                            JSONObject jsonObject = new JSONObject(objectMapper.writeValueAsString(principal));
//                            user.setUsername(jsonObject.getString("username"));
//                            user.setUser_id(jsonObject.getInt("user_id"));
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//                        RequestCache cache = new HttpSessionRequestCache();
//                        SavedRequest savedRequest = cache.getRequest(httpServletRequest,httpServletResponse);
//                        System.out.println(user);
//                        if (user.getUsername().equals("admin"))
//                        {
//                            map.put("msg","fail");
//                            httpServletResponse.sendRedirect("/users");
//
//                        }
//                        out.write(objectMapper.writeValueAsString(map));
//                        out.flush();
//                        out.close();
//                        httpServletRequest.getSession().setAttribute("userId",user.getUser_id());
//                    }
//                })
//                .failureHandler(new AuthenticationFailureHandler() {
//                    @Override
//                    public void onAuthenticationFailure(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, AuthenticationException e) throws IOException, ServletException {
//                        log.debug("--------login fail---------");
//                        PrintWriter out = httpServletResponse.getWriter();
//                        httpServletResponse.setStatus(401);
//                        Map<String,Object> map = new HashMap<>();
//                        map.put("status",401);
//                        map.put("msg","fail");
//                        ObjectMapper objectMapper = new ObjectMapper();
//                        out.write(objectMapper.writeValueAsString(map));
//                        out.flush();
//                        out.close();
//                    }
//                })
//                .permitAll()
//                .and()
//                .httpBasic()
//                .and()
//                .csrf()
//                .disable();
//    }

}
