package com.sparcs.team1.domain.mooddiary.controller;

import com.sparcs.team1.domain.mooddiary.dto.CreateAnswerRequest;
import com.sparcs.team1.domain.mooddiary.dto.CreateAnswerResponse;
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
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "감정 기록 API")
public interface MoodDiaryApi {

    @Operation(
            summary = "Speech-To-Summary-Text API",
            description = "네이버 클라우드 플랫폼의 Clova Speech와 Clova Studio를 이용해 음성을 텍스트 요약본으로 변환합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = MediaType.MULTIPART_FORM_DATA_VALUE,
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
                            responseCode = "40006",
                            description = "응답 값을 객체로 변환하는 데 실패하였습니다.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "40402",
                            description = "존재하지 않는 회원입니다.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "40403",
                            description = "Response Body가 존재하지 않습니다.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "50001",
                            description = "FEIGN 에러가 발생하였습니다.",
                            content = @Content
                    )
            }
    )
    @PostMapping("/diary")
    public ResponseEntity<CreateDiaryResponse> createMoodDiary(
            @ModelAttribute CreateDiaryRequest createDiaryRequest
    );

    @Operation(
            summary = "챗봇 답변 생성 API",
            description = "사용자의 기록을 바탕으로 선택한 어시스턴트한테 적절한 답변을 받습니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE,
                                    schema = @Schema(
                                            implementation = CreateAnswerResponse.class
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "40006",
                            description = "응답 값을 객체로 변환하는 데 실패하였습니다.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "40403",
                            description = "Response Body가 존재하지 않습니다.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "40404",
                            description = "존재하지 않는 감정 기록입니다.",
                            content = @Content
                    ),
                    @ApiResponse(
                            responseCode = "50001",
                            description = "FEIGN 에러가 발생하였습니다.",
                            content = @Content
                    )
            }
    )
    @PostMapping("/answer")
    public ResponseEntity<CreateAnswerResponse> createAnswer(
            @RequestBody CreateAnswerRequest createAnswerRequest
    );
}
