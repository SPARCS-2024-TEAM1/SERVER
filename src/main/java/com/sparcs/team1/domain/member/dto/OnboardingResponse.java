package com.sparcs.team1.domain.member.dto;

import com.fasterxml.jackson.annotation.JsonInclude;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record OnboardingResponse(
        Long memberId,
        String nickname
) {
    public static OnboardingResponse of(
            final Long memberId,
            final String nickname
    ) {
        return new OnboardingResponse(
                memberId,
                nickname
        );
    }
}
