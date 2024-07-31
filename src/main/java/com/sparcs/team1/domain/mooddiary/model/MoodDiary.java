package com.sparcs.team1.domain.mooddiary.model;

import com.sparcs.team1.domain.member.model.Member;
import com.sparcs.team1.global.common.model.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "mood_diary")
public class MoodDiary extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "member_id", referencedColumnName = "id", nullable = false)
    private Member member;

    @Enumerated(EnumType.STRING)
    @Column(name = "mood", nullable = false)
    private Mood mood;

    @Enumerated(EnumType.STRING)
    @Column(name = "assistant", nullable = false)
    private Assistant assistant;

    @Column(name = "diary", length = 500)
    private String diary;

    @Column(name = "summary")
    private String summary;

    @Column(name = "answer", length = 500)
    private String answer;

    @Builder
    private MoodDiary(
            Member member,
            Mood mood,
            Assistant assistant,
            String diary,
            String summary,
            String answer
    ) {
        this.member = member;
        this.mood = mood;
        this.assistant = assistant;
        this.diary = diary;
        this.summary = summary;
        this.answer = answer;
    }

    public void updateDiary(String diary) {
        if (diary != null) {
            this.diary = diary;
        }
    }

    public void updateSummary(String summary) {
        if (summary != null) {
            this.summary = summary;
        }
    }
}
