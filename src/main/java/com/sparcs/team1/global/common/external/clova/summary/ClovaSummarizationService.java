package com.sparcs.team1.global.common.external.clova.summary;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClovaSummarizationService {

    private final ClovaSummarizationClient clovaSummarizationClient;

    @Value("${naver.clova.summary.api.key}")
    private String apiKey;

    @Value("${naver.clova.summary.api.clovaStudio}")
    private String clovaStudioApiKey;

    @Value("${naver.clova.summary.api.gwApiKey}")
    private String apiGwApiKey;

    public SummarizationResponse summarizeTexts(String[] texts) {
        SummarizationRequest request = SummarizationRequest.of(
                texts,
                true,
                -1,
                1000,
                true,
                300,
                false
        );

        return clovaSummarizationClient.summarizeTexts(apiKey, clovaStudioApiKey, apiGwApiKey, request);
    }
}
