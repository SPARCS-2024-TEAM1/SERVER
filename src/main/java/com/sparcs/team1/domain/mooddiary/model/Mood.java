package com.sparcs.team1.domain.mooddiary.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Mood {

    JOY("JOY"),
    ANXIETY("ANXIETY"),
    STRESS("STRESS"),
    SADNESS("SADNESS"),
    CALMNESS("CALMNESS"),
    GRATITUDE("GRATITUDE"),
    ANGER("ANGER"),
    FATIGUE("FATIGUE"),
    LONELINESS("LONELINESS"),
    ;

    private final String mood;
}
