package com.session.memo.entity;

import com.session.user.entity.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "memos")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Memo {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String text;

    // 사용되는 수 -> 유저보다 메모가 더 많다. : 따라서 연관성은 메모에 붙여줌
    // 연관 관계의 주인 = memo ( 유저 1명당 메모 N개 )
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Memo(String text, User user) {

        this.text = text;
        this.user = user;
    }

}
