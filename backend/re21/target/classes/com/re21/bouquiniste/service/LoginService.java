package com.re21.bouquiniste.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.re21.bouquiniste.dao.RoleMapper;
import com.re21.bouquiniste.dao.UserMapper;
import com.re21.bouquiniste.modules.Role;
import com.re21.bouquiniste.modules.User;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
@Slf4j
public class LoginService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

    public String userLogin(String username,String password){
        if (userMapper.loginUser(username,password) == 0)
        {
            return "login fail";
        }
        User user = userMapper.getUserByUsername(username);

        List<Role> roles = roleMapper.getRoleByUserId(user.getUser_id());
        user.setRoles(roles);

        try {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(user);
        }catch (Exception e)
        {

            log.debug("unknown login error");
            return "login fail";
        }

    }
}



















