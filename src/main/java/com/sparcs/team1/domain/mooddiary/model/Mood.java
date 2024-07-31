package com.sparcs.team1.domain.mooddiary.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum Mood {

    기쁨("기쁨"),
    불안("불안"),
    스트레스("스트레스"),
    슬픔("슬픔"),
    평온("평온"),
    감사("감사"),
    화남("화남"),
    피로("피로"),
    외로움("외로움"),
    ;

    private final String mood;
}
