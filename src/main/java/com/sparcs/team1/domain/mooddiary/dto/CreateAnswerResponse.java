package com.sparcs.team1.domain.mooddiary.dto;

public record CreateAnswerResponse(
        Long memberId,
        String answer,
        String summary
) {
    public static CreateAnswerResponse of(
            final Long memberId,
            final String answer,
            final String summary
    ) {
        return new CreateAnswerResponse(
                memberId,
                answer,
                summary
        );
    }
}
