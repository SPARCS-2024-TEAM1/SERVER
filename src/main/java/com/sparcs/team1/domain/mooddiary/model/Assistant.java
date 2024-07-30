package com.sparcs.team1.domain.mooddiary.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Assistant {

    VICKY("VICKY"),
    REALLY("REALLY"),
    BANGBANG("BANGBANG"),
    ;

    private final String assistant;
}
