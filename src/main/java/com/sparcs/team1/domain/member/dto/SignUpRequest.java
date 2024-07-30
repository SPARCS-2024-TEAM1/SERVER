package com.sparcs.team1.domain.member.dto;

import jakarta.validation.constraints.NotBlank;

public record SignUpRequest(
        @NotBlank(message = "닉네임은 공백일 수 없습니다.")
        String nickname,
        @NotBlank(message = "전화번호는 공백일 수 없습니다.")
        String phoneNumber
) {
}
