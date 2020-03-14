package com.re21.bouquiniste.dao;

import com.re21.bouquiniste.modules.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface UserMapper {
    User getUserByUserId(Integer id);
    User getUserByUsername(String username);
    int insertUser(User user);
    int updateUser(User user);
    int getSize();
    List<User> getAllUser();
    int loginUser(@Param("username") String username, @Param("password") String password);
}
