package com.ssafy.questory.dto.response.ranking;

import com.ssafy.questory.domain.Ranking;
import lombok.Builder;
import lombok.Getter;

@Getter
public class MemberRankingResponseDto {
    private int rank;
    private String nickname;
    private int exp;

    @Builder
    private MemberRankingResponseDto(int rank, String nickname, int exp) {
        this.rank = rank;
        this.nickname = nickname;
        this.exp = exp;
    }

    public static MemberRankingResponseDto from(Ranking ranking) {
        return MemberRankingResponseDto.builder()
                .rank(ranking.getRank())
                .nickname(ranking.getNickname())
                .exp(ranking.getExp())
                .build();
    }
}
