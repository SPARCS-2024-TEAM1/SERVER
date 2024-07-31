package com.sparcs.team1.domain.mooddiary.repository;

import com.sparcs.team1.domain.mooddiary.model.MoodDiary;
import com.sparcs.team1.global.exception.enums.ErrorType;
import com.sparcs.team1.global.exception.model.CustomException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodDiaryRepository extends JpaRepository<MoodDiary, Long> {

    Optional<MoodDiary> findMoodDiaryById(Long id);

    default MoodDiary findMemberByIdOrThrow(Long id) {
        return findMoodDiaryById(id)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_MOOD_DIARY_ERROR));
    }
}
