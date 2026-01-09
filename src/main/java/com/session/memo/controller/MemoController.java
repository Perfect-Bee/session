package com.session.memo.controller;

import com.session.memo.dto.CreateMemoRequest;
import com.session.memo.dto.CreateMemoResponse;
import com.session.memo.service.MemoService;
import com.session.user.dto.SessionUser;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class MemoController {

    private final MemoService memoService;

    @PostMapping("/memos")
    public ResponseEntity<CreateMemoResponse> create(
            // 2. 로그인 한 사람만 메모 입력할 수 있도록
            @SessionAttribute(name = "loginUser", required = false) SessionUser sessionUser,
            @RequestBody CreateMemoRequest request
    ) {
        //                                                  3-1. request -> sessionUser, request
        return ResponseEntity.status(HttpStatus.CREATED).body(memoService.save(sessionUser, request));

    }
}
