package com.sparcs.team1.domain.member.controller;

import com.sparcs.team1.domain.member.dto.OnboardingResponse;
import com.sparcs.team1.domain.member.dto.SendCodeRequest;
import com.sparcs.team1.domain.member.dto.SignUpRequest;
import com.sparcs.team1.domain.member.dto.VerifyCodeRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Tag(name = "회원가입/로그인 API")
public interface MemberApi {

    @Operation(
            summary = "문자 발송 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "40001",
                            description = "유효하지 않은 요청입니다.(Valid 실패)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "40002",
                            description = "잘못된 휴대전화 번호 양식입니다.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PostMapping("/phone/code")
    public ResponseEntity<Void> sendCode(
            @Valid @RequestBody SendCodeRequest sendCodeRequest
    );

    @Operation(
            summary = "문자 발송 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "40001",
                            description = "유효하지 않은 요청입니다.(Valid 실패)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "40003",
                            description = "인증번호가 올바르지 않습니다.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "40401",
                            description = "인증 요청 이력이 존재하지 않습니다.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "40402",
                            description = "존재하지 않는 회원입니다.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PostMapping("/phone/verification")
    public ResponseEntity<OnboardingResponse> verifyCode(
            @Valid @RequestBody VerifyCodeRequest verifyCodeRequest
    );

    @Operation(
            summary = "문자 발송 API",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "40001",
                            description = "유효하지 않은 요청입니다.(Valid 실패)",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    ),
                    @ApiResponse(
                            responseCode = "40004",
                            description = "닉네임이 조건을 만족하지 않습니다.",
                            content = @Content(
                                    mediaType = MediaType.APPLICATION_JSON_VALUE
                            )
                    )
            }
    )
    @PostMapping("/sign-up")
    public ResponseEntity<OnboardingResponse> signUp(
            @Valid @RequestBody SignUpRequest signUpRequest
    );
}
