package com.sparcs.team1.domain.mooddiary.dto;

public record CreateDiaryResponse(
        Long moodDiaryId,
        String summary
) {
    public static CreateDiaryResponse of(
            final Long moodDiaryId,
            final String summary
    ) {
        return new CreateDiaryResponse(
                moodDiaryId,
                summary
        );
    }
}
