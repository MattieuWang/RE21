package com.re21.bouquiniste.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@NoArgsConstructor
//@AllArgsConstructor
@Component
public class User {
    private String username;
    private String password;
    private Integer user_id;
    @Value("???")
    private String phone;
    @Value("?@?.com")
    private String email;
    private List<Role> roles = new ArrayList<>();
//    private boolean state;  //是否在线


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
}
