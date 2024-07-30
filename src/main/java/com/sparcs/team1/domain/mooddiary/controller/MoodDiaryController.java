package com.sparcs.team1.domain.mooddiary.controller;

import com.sparcs.team1.domain.mooddiary.dto.CreateDiaryRequest;
import com.sparcs.team1.domain.mooddiary.dto.CreateDiaryResponse;
import com.sparcs.team1.domain.mooddiary.service.MoodDiaryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MoodDiaryController {

    private final MoodDiaryService moodDiaryService;

    @PostMapping("/diary")
    public ResponseEntity<CreateDiaryResponse> createMoodDiary(
            @ModelAttribute CreateDiaryRequest createDiaryRequest
    ) {
        return ResponseEntity.ok(moodDiaryService.createMoodDiary(createDiaryRequest));
    }
}
