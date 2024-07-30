package com.sparcs.team1.domain.mooddiary.repository;

import com.sparcs.team1.domain.mooddiary.model.MoodDiary;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MoodDiaryRepository extends JpaRepository<MoodDiary, Long> {
}
