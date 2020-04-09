package com.re21.bouquiniste.modules;

import com.re21.bouquiniste.dao.RoleMapper;
import com.re21.bouquiniste.dao.UserMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@AllArgsConstructor
@Component
public class User implements UserDetails {
    private String username;
    private String password;
    private Integer user_id;
    @Value("???")
    private String phone;
    @Value("?@?.com")
    private String email;
    private List<Role> roles = new ArrayList<>();
//    private boolean state;  //是否在线


    public User() {
    }

    public User(String username, String password, List<Role> roles) {
        this.username = username;
        this.password = password;
        this.roles = roles;
        phone = "06????????";
        email = "????@???.com";
    }

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        phone = "06????????";
        email = "????@???.com";
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        List<GrantedAuthority>authorities = new ArrayList<>();
//        user_id = userMapper.getUserByUsername(username).getUser_id();
//        roles = roleMapper.getRoleByUserId(user_id);
//        for (Role role : roles)
//        {
//            authorities.add(new SimpleGrantedAuthority("ROLE_"+role.getRole()));
//        }
        authorities.add(new SimpleGrantedAuthority("ROLE_"+"user"));
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
