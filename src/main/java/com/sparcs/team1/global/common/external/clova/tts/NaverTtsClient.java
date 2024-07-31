package com.sparcs.team1.global.common.external.clova.tts;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "naverTtsClient", url = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1")
public interface NaverTtsClient {

    @PostMapping(value = "/tts", consumes = "application/x-www-form-urlencoded")
    byte[] generateSpeech(
            @RequestHeader("X-NCP-APIGW-API-KEY-ID") final String clientId,
            @RequestHeader("X-NCP-APIGW-API-KEY") final String clientSecret,
            @RequestParam("speaker") final String speaker,
            @RequestParam("text") final String text,
            @RequestParam("volume") final int volume,
            @RequestParam("speed") final int speed,
            @RequestParam("pitch") final int pitch,
            @RequestParam("emotion") final int emotion,
            @RequestParam("emotion-strength") final int emotionStrength,
            @RequestParam("format") final String format,
            @RequestParam("alpha") final int alpha,
            @RequestParam("end-pitch") final int endPitch
    );
}
