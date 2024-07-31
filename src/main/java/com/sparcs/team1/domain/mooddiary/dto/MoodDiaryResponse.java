package com.sparcs.team1.domain.mooddiary.dto;

import com.sparcs.team1.domain.mooddiary.model.Assistant;

public record MoodDiaryResponse(
        Long moodDiaryId,
        Assistant assistant,
        String answer,
        String summary
) {
    public static MoodDiaryResponse of(
            final Long moodDiaryId,
            final Assistant assistant,
            final String answer,
            final String summary
    ) {
        return new MoodDiaryResponse(
                moodDiaryId,
                assistant,
                answer,
                summary
        );
    }
}
