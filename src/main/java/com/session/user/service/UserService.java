package com.session.user.service;

import com.session.user.dto.SigninUserRequest;
import com.session.user.dto.SigninUserResponse;
import com.session.user.dto.SignupUserRequest;
import com.session.user.dto.SignupUserResponse;
import com.session.user.entity.User;
import com.session.user.repository.UserRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    @Transactional
    public SignupUserResponse save(SignupUserRequest request) {
        User user = new User(
                request.getEmail(),
                request.getPassword()
        );
        User savedUser = userRepository.save(user);
        // 비밀번호는 response XXX)
        return new SignupUserResponse(
                savedUser.getId(),
                savedUser.getEmail()
        );
    }

    @Transactional
    public SigninUserResponse signin(@Valid SigninUserRequest request) {
        // UserRepository에 커스텀 쿼리 매서드 생성
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new IllegalStateException("없는 이메일 입니다.")
        );
        // user의 email과 request의 email의 비교가 필요하다.
        // 해설 세션에서 나온 기능
        // ObjectUtils.nullSafeEquals : 객체가 null이어도 안터짐 + 둘 다 null이어도 같은지만 비교
        // 비밀번호가 같지 않으면
        if (!ObjectUtils.nullSafeEquals(user.getPassword(), request.getPassword())) {
            throw new IllegalStateException("비밀번호가 일치하지 않습니다.");
        }
        //++ 왜 request.getPassword()와 user.getPassword()를 A.equals(B)로 하지 않는가?>
        // -> 둘 중 하나라도 null이면 터짐. ObjectUtils.nullSafeEquals은 안터짐. 이름부터가 null에서 safe하다.

        // 비밀번호가 같으면
        return new  SigninUserResponse(
                user.getId(),
                user.getEmail()
        );
    }
}
