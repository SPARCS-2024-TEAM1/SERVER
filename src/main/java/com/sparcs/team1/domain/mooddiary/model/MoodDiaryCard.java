package com.sparcs.team1.domain.mooddiary.model;

public record MoodDiaryCard(
        Long moodDiaryId,
        String diaryDate,
        Mood mood
) {
}
