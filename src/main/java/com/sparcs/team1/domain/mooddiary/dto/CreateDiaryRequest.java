package com.sparcs.team1.domain.mooddiary.dto;

import com.sparcs.team1.domain.mooddiary.model.Assistant;
import com.sparcs.team1.domain.mooddiary.model.Mood;
import org.springframework.web.multipart.MultipartFile;

public record CreateDiaryRequest(
        Long memberId,
        Mood mood,
        Assistant assistant,
        MultipartFile file
) {
}
