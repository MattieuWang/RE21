package com.re21.bouquiniste.dao;

import com.re21.bouquiniste.modules.Role;
import com.re21.bouquiniste.modules.Role_User;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Mapper
@Repository
public interface RoleMapper {
    List<Role> getRoleByRoleId(Integer id);
    List<Role> getRoleByUserId(Integer user_id);
    int insertRoleUser(Role_User role_user);
    int getSize();
    int insertRole(Role role);
}
