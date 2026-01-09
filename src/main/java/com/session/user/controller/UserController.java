package com.session.user.controller;

import com.session.user.dto.*;
import com.session.user.service.UserService;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.SessionAttribute;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    // 회원가입
    @PostMapping("/signup")
    public ResponseEntity<SignupUserResponse> signup(
            @RequestBody SignupUserRequest request
    ) {
        return ResponseEntity.status(HttpStatus.CREATED).body(userService.save(request)); // 서비스-요청 -> 응답
    }

    // 로그인
    // PostMapping : 이도저도 아닌 에매한 것에 쓸 수 있다.(등록도, 조회도, 수정도, 삭제도 아님 -> 애매함 = Post)
    @PostMapping("/signin")
    // 여기서 SigninUserResponse 필요 없다고 판단하심 : 지움
    // 로그인은 요청 후 응답이 필요 없으니까?
    // -> 또 필요하다는데...? 40줄
    public ResponseEntity<String> signin(
            // 로그인 DTO 코드 참고(복붙)
            @Valid @RequestBody SigninUserRequest request, HttpSession session
    ) {
        // 응답용으로 쓸 건 아니니까 result로
        SigninUserResponse result = userService.signin(request);
        // 구태여 Session을 만드는 이유는?
        // 서비스 입장에서 세션을 알고 싶지 않음(분리)
        // + 우연히 SigninUserResponse와 SessionUser이 id와 email가 같은 것.
        // 다를 수 있음
        SessionUser sessionUser = new SessionUser(
                result.getId(),
                result.getEmail()
        );
        session.setAttribute("loginUser", sessionUser);
        // return 만들고 싶으니
        // public void signin( -> public ResponseEntity<String> signin(하고
        // session.setAttribute 아래에 return ResponseEntity.ok("sucess"); 하면 된다.
        return ResponseEntity.ok("sucess");
    }

    // 로그아웃
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @SessionAttribute(name = "loginUser", required = false)
            SessionUser sessionUser,
            HttpSession session
    ) {
        if (sessionUser == null) {
            return ResponseEntity.badRequest().build();
        }

        session.invalidate();
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}

