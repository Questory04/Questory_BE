package com.ssafy.questory.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
public class Ranking {
    private int rank;
    private String nickname;
    private int exp;

    protected Ranking() {}

    @Builder
    private Ranking(int rank, String nickname, int exp) {
        this.rank = rank;
        this.nickname = nickname;
        this.exp = exp;
    }
}
