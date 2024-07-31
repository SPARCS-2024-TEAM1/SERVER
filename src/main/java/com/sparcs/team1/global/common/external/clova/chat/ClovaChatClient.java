package com.sparcs.team1.global.common.external.clova.chat;


import jakarta.validation.Valid;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "clovaChatClient", url = "https://clovastudio.stream.ntruss.com/testapp/v1/chat-completions")
public interface ClovaChatClient {

    @PostMapping("/{apiKey}")
    ChatResponse sendChatRequest(
            @RequestParam("apiKey") final String apiKey,
            @RequestHeader("X-NCP-CLOVASTUDIO-API-KEY") final String clovaStudioApiKey,
            @RequestHeader("X-NCP-APIGW-API-KEY") final String apiGwApiKey,
            @Valid @RequestBody ChatRequest request
    );
}
