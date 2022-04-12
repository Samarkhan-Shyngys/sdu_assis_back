package com.sdu.edu.pojo;

import lombok.Data;

@Data
public class UserDto {
    private Long id;
    private String email;
    private String role;
    private String username;

    public UserDto() {
    }

    public UserDto(Long id, String email,String username, String role) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.username = username;
    }
}
