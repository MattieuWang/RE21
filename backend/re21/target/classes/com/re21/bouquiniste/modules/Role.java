package com.re21.bouquiniste.modules;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
//@AllArgsConstructor
@NoArgsConstructor
@Component
public class Role {
    private Integer role_id;
    @Value("user")
    private String role;

    public Role(Integer role_id) {
        this.role_id = role_id;
        role = "user";
    }

}
