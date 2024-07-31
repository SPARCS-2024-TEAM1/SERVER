package com.sparcs.team1.domain.mooddiary.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Assistant {

    동글이("동글이"),
    뾰족이("뾰족이"),
    ;

    private final String assistant;
}
