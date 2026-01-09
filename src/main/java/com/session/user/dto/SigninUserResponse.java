package com.session.user.dto;

import lombok.Getter;

@Getter
public class SigninUserResponse {

    private final Long id;
    private final String email;

    public SigninUserResponse(Long id, String email) {
        this.id = id;
        this.email = email;
    }
}
