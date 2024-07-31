package com.sparcs.team1.global.common.external.clova.chat;

import jakarta.validation.constraints.NotBlank;

public record ChatMessage(
        @NotBlank(message = "대화 메시지 역할은 공백일 수 없습니다.")
        String role,
        @NotBlank(message = "대화 메시지 내용은 공백일 수 없습니다.")
        String content
) {
}
