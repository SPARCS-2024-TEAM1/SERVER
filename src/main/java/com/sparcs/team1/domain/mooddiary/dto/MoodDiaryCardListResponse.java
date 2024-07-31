package com.sparcs.team1.domain.mooddiary.dto;

import com.sparcs.team1.domain.mooddiary.model.MoodDiaryCard;
import java.util.List;

public record MoodDiaryCardListResponse(
        List<MoodDiaryCard> moodDiaryCards
) {
    public static MoodDiaryCardListResponse of(
            final List<MoodDiaryCard> moodDiaryCards
    ) {
        return new MoodDiaryCardListResponse(
                moodDiaryCards
        );
    }
}
