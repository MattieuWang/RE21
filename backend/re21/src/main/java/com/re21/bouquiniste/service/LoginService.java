package com.re21.bouquiniste.service;

import com.re21.bouquiniste.dao.RoleMapper;
import com.re21.bouquiniste.dao.UserMapper;
import com.re21.bouquiniste.modules.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class LoginService implements UserDetailsService  {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userMapper.getUserByUsername(username);

        return user;

    }
}



















