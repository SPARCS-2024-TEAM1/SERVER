package com.sparcs.team1.global.common.external.clova.tts;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverTtsService {

    private final NaverTtsClient naverTtsClient;

    @Value("${naver.tts.api.client.id}")
    private String clientId;

    @Value("${naver.tts.api.client.secret}")
    private String clientSecret;

    public File convertTextToSpeechDG(String text) {
        byte[] audioContent = naverTtsClient.generateSpeech(
                clientId,
                clientSecret,
                "nkyuwon",
                text,
                0,
                0,
                0,
                0,
                0,
                "mp3",
                0,
                0
        );

        // 랜덤한 이름으로 mp3 파일 생성
        String tempName = Long.valueOf(new Date().getTime()).toString();
        File outputFile = new File(tempName + ".mp3");

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(audioContent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return outputFile;
    }

    public File convertTextToSpeechPJ(String text) {
        byte[] audioContent = naverTtsClient.generateSpeech(
                clientId,
                clientSecret,
                "dara_ang",
                text,
                0,
                -1,
                -2,
                0,
                0,
                "mp3",
                -1,
                0
        );

        // 랜덤한 이름으로 mp3 파일 생성
        String tempName = Long.valueOf(new Date().getTime()).toString();
        File outputFile = new File(tempName + ".mp3");

        try (FileOutputStream outputStream = new FileOutputStream(outputFile)) {
            outputStream.write(audioContent);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return outputFile;
    }
}

