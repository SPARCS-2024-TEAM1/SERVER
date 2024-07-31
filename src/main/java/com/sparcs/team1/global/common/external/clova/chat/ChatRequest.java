package com.sparcs.team1.global.common.external.clova.chat;

import jakarta.validation.constraints.NotEmpty;

public record ChatRequest(
        @NotEmpty(message = "대화 메시지 목록은 공백일 수 없습니다.")
        ChatMessage[] messages,
        double temperature,
        int topK,
        double topP,
        double repeatPenalty,
        int maxTokens
) {
}
