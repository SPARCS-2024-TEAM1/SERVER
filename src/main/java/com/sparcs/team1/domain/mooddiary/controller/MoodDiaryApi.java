package com.sparcs.team1.domain.mooddiary.controller;

import com.sparcs.team1.domain.mooddiary.dto.CreateDiaryRequest;
import com.sparcs.team1.domain.mooddiary.dto.CreateDiaryResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Tag(name = "Mood Diary Api") // 임시
public interface MoodDiaryApi {

    @Operation(
            summary = "Speech-To-Text API",
            description = "네이버 클라우드 플랫폼의 Clova Speech를 이용해 음성을 텍스트로 변환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = CreateDiaryResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "40005",
                            description = "파일 업로드에 실패하였습니다.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "40402",
                            description = "존재하지 않는 회원입니다.",
                            content = @Content
                    )
            }
    )
    @PostMapping("/diary")
    public ResponseEntity<CreateDiaryResponse> createMoodDiary(
            @ModelAttribute CreateDiaryRequest createDiaryRequest
    );
}
