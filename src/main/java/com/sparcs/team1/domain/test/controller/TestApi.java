package com.sparcs.team1.domain.test.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;

@Tag(name = "스웨거 및 HTTPS 테스트용 API")
public interface TestApi {

    @Operation(
            summary = "스웨거 및 HTTPS 테스트용 API",
            description = "스웨거 및 HTTPS를 테스트하기 위한 API입니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "스웨거 및 HTTPS 테스트 성공"
                    )
            }
    )
    public ResponseEntity<String> testHttps();
}
