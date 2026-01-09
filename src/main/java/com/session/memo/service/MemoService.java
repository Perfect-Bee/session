package com.session.memo.service;

import com.session.memo.dto.CreateMemoRequest;
import com.session.memo.dto.CreateMemoResponse;
import com.session.memo.entity.Memo;
import com.session.memo.repository.MemoRepository;
import com.session.user.dto.SessionUser;
import com.session.user.entity.User;
import com.session.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemoService {
    private final MemoRepository memoRepository;

    // 4. Memo에 넣을 session을 위해 레포지토리에서 찾아줄거다
    private final UserRepository userRepository;

    @Transactional
    // 3-2. CreateMemoRequest request -> SessionUser sessionuser, createMemoRequest request
    public CreateMemoResponse save(SessionUser sessionuser, CreateMemoRequest request) {
        // 4-1. Memo에 넣을 거
        User user = userRepository.findById(sessionuser.getId()).orElseThrow(
                                // 이메일, 아이디 둘 다 되는데, 아이다가 조금 더 좋다. : PK로 찾는 게 가장 빠름
                () -> new IllegalStateException("없는 유저")
        );

        // 1. 로그인 한 사람만 메모를 쓸 수 있게 해야 함 -> UserController에서 세션 복붙(memoController)로 이동하기
        Memo memo = new Memo(request.getText(), user); // 5. user 추가
        Memo savedMemo = memoRepository.save(memo);
        return new CreateMemoResponse(
                savedMemo.getId(),
                savedMemo.getText()
        );
    }
}
