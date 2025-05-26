package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Ranking {
    private int rank;
    private String email;
    private String nickname;
    private int exp;

    protected Ranking() {}

    @Builder
    private Ranking(int rank, String email, String nickname, int exp) {
        this.rank = rank;
        this.email = email;
        this.nickname = nickname;
        this.exp = exp;
    }
}
