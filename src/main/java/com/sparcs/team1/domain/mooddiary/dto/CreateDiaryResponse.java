package com.sparcs.team1.domain.mooddiary.dto;

public record CreateDiaryResponse(
        Long moodDiaryId,
        String diary
) {
    public static CreateDiaryResponse of(
            final Long moodDiaryId,
            final String diary
    ) {
        return new CreateDiaryResponse(
                moodDiaryId,
                diary
        );
    }
}
