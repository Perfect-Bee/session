package com.session.memo.dto;

import lombok.Getter;

@Getter
public class CreateMemoResponse {
    private final Long id;
    private final String text;

    public CreateMemoResponse(Long id, String text) {
        this.id = id;
        this.text = text;
    }
}
