package com.re21.bouquiniste.service;

import com.re21.bouquiniste.dao.RoleMapper;
import com.re21.bouquiniste.dao.UserMapper;
import com.re21.bouquiniste.modules.Role;
import com.re21.bouquiniste.modules.Role_User;
import com.re21.bouquiniste.modules.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class UserService {

    @Resource
    private UserMapper userMapper;
    @Resource
    private RoleMapper roleMapper;

    @Autowired
    private User cntUser;   //current user

    public int updateUser(User user)
    {
         return userMapper.updateUser(user);    //0 fail
    }

    public User getUserById(Integer id)
    {
        return userMapper.getUserByUserId(id);
    }

    public User getUserByUsername(String username)
    {
        return userMapper.getUserByUsername(username);
    }

    public String getAllUserByString()
    {
        String msg = "";
        List<User> users = userMapper.getAllUser();
        for (User user: users)
        {
            msg += user.toString()+" \n";
        }
        return msg;
    }

    public int addUser(User user)
    {
        user.setUser_id(userMapper.getSize()+1);
        if (userMapper.getUserByUsername(user.getUsername())!=null)
        {
            return 0;
        }
        if (user.getRoles().size()==0 || user.getRoles()==null)
        {
            List<Role> roles = new ArrayList<>();
            Role role = new Role(roleMapper.getSize()+1);
            roles.add(role);
            cntUser.setRoles(roles);

        }
        System.out.println(user);
        cntUser.setUser_id(user.getUser_id());
        cntUser.setUsername(user.getUsername());
        cntUser.setPassword(user.getPassword());
        if(userMapper.insertUser(cntUser)>0 && roleMapper.insertRole(cntUser.getRoles().get(0))>0 && roleMapper.insertRoleUser(new Role_User(cntUser.getUser_id(),cntUser.getRoles().get(0).getRole_id()))>0)
        {
            return 1;
        }
        else
        {
            return 0;
        }
    }
}










