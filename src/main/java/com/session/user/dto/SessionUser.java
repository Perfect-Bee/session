package com.session.user.dto;

import lombok.Getter;

@Getter
public class SessionUser {
    private Long id;
    private String email;

    public  SessionUser(Long id, String email) {
        this.id = id;
        this.email = email;
    }

}
