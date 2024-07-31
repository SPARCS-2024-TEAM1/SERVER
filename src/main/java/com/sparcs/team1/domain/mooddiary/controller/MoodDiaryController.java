package com.sparcs.team1.domain.mooddiary.controller;

import com.sparcs.team1.domain.mooddiary.dto.CreateAnswerRequest;
import com.sparcs.team1.domain.mooddiary.dto.CreateAnswerResponse;
import com.sparcs.team1.domain.mooddiary.dto.CreateAudioRequest;
import com.sparcs.team1.domain.mooddiary.dto.CreateDiaryRequest;
import com.sparcs.team1.domain.mooddiary.dto.CreateDiaryResponse;
import com.sparcs.team1.domain.mooddiary.service.MoodDiaryService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MoodDiaryController implements MoodDiaryApi {

    private final MoodDiaryService moodDiaryService;

    @Override
    @PostMapping("/diary")
    public ResponseEntity<CreateDiaryResponse> createMoodDiary(
            @ModelAttribute final CreateDiaryRequest createDiaryRequest
    ) {
        return ResponseEntity.ok(moodDiaryService.createMoodDiary(createDiaryRequest));
    }

    @PostMapping("/answer")
    public ResponseEntity<CreateAnswerResponse> createAnswer(
            @RequestBody final CreateAnswerRequest createAnswerRequest
    ) {
        return ResponseEntity.ok(moodDiaryService.createAnswer(createAnswerRequest));
    }

    @PostMapping("/audio")
    public ResponseEntity<StreamingResponseBody> createAudio(
            @RequestBody final CreateAudioRequest createAudioRequest
    ) {
        File audioFile = moodDiaryService.createAudio(createAudioRequest).audioFile();

        if (audioFile == null || !audioFile.exists()) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        StreamingResponseBody responseBody = outputStream -> {
            try (FileInputStream inputStream = new FileInputStream(audioFile)) {
                byte[] buffer = new byte[4096];
                int bytesRead;
                while ((bytesRead = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, bytesRead);
                }
                outputStream.flush();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                audioFile.delete(); // 파일을 스트리밍 후 삭제
            }
        };

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + audioFile.getName());
        headers.add(HttpHeaders.CONTENT_TYPE, "audio/mpeg");

        return ResponseEntity.ok()
                .headers(headers)
                .body(responseBody);
    }
}
