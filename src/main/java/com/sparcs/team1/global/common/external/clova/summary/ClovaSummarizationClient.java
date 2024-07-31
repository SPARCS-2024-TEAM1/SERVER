package com.sparcs.team1.global.common.external.clova.summary;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "clovaSummarizationClient", url = "https://clovastudio.apigw.ntruss.com/testapp/v1/api-tools/summarization/v2")
public interface ClovaSummarizationClient {

    @PostMapping("/{apiKey}")
    SummarizationResponse summarizeTexts(
            @RequestParam("apiKey") final String apiKey,
            @RequestHeader("X-NCP-CLOVASTUDIO-API-KEY") final String clovaStudioApiKey,
            @RequestHeader("X-NCP-APIGW-API-KEY") final String apiGwApiKey,
            @RequestBody final SummarizationRequest request
    );
}
