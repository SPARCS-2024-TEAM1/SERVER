package com.sparcs.team1.global.common.external.clova.summary;

import jakarta.validation.constraints.NotEmpty;

public record SummarizationRequest(
        @NotEmpty(message = "요약할 문장 목록은 공백일 수 없습니다.")
        String[] texts,
        boolean autoSentenceSplitter,
        int segCount,
        int segMaxSize,
        boolean postProcess,
        int segMinSize,
        boolean includeAiFilters
) {
    public static SummarizationRequest of(
            final String[] texts,
            final boolean autoSentenceSplitter,
            final int segCount,
            final int segMaxSize,
            final boolean postProcess,
            final int segMinSize,
            final boolean includeAiFilters
    ) {
        return new SummarizationRequest(
                texts,
                autoSentenceSplitter,
                segCount,
                segMaxSize,
                postProcess,
                segMinSize,
                includeAiFilters
        );
    }
}
