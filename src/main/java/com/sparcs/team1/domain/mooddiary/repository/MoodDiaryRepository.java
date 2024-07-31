package com.sparcs.team1.domain.mooddiary.repository;

import com.sparcs.team1.domain.mooddiary.model.MoodDiary;
import com.sparcs.team1.global.exception.enums.ErrorType;
import com.sparcs.team1.global.exception.model.CustomException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodDiaryRepository extends JpaRepository<MoodDiary, Long> {

    Optional<MoodDiary> findMoodDiaryById(Long id);

    default MoodDiary findMoodDiaryByIdOrThrow(Long id) {
        return findMoodDiaryById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_MOOD_DIARY_ERROR));
    }

    // CreatedAt을 기준으로 오름차순 정렬된, answer가 존재하는 MoodDiary 리스트를 조회
    List<MoodDiary> findAllMoodDiaryByMemberIdAndAnswerIsNotNullOrderByCreatedAtAsc(Long memberId);

    // 특정 멤버의 오늘 날짜에 해당하는 MoodDiary를 조회
    default MoodDiary findTodayMoodDiaryByMemberIdOrThrow(Long memberId) {
        LocalDateTime startOfDay = LocalDate.now().atStartOfDay();
        LocalDateTime now = LocalDateTime.now();

        return findMoodDiaryByMemberIdAndCreatedAtBetween(memberId, startOfDay, now)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_MOOD_DIARY_ERROR));
    }

    Optional<MoodDiary> findMoodDiaryByMemberIdAndCreatedAtBetween(Long memberId, LocalDateTime start,
                                                                   LocalDateTime end);
}
