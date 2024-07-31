package com.sparcs.team1.global.common.external.clova.tts;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.Date;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class NaverTtsService {

    @Value("${naver.tts.api.client.id}")
    private String clientId;

    @Value("${naver.tts.api.client.secret}")
    private String clientSecret;

    public File generateSpeech(String text, String speaker, int volume, int speed, int pitch) throws IOException {
        try {
            String encodedText = URLEncoder.encode(text, "UTF-8");
            String apiURL = "https://naveropenapi.apigw.ntruss.com/tts-premium/v1/tts";
            URL url = new URL(apiURL);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("POST");
            con.setRequestProperty("X-NCP-APIGW-API-KEY-ID", clientId);
            con.setRequestProperty("X-NCP-APIGW-API-KEY", clientSecret);

            // POST 요청 파라미터 설정
            String postParams = String.format("speaker=%s&volume=%d&speed=%d&pitch=%d&format=mp3&text=%s",
                    speaker, volume, speed, pitch, encodedText);
            con.setDoOutput(true);
            try (DataOutputStream wr = new DataOutputStream(con.getOutputStream())) {
                wr.writeBytes(postParams);
                wr.flush();
            }

            int responseCode = con.getResponseCode();
            if (responseCode == 200) { // 정상 호출
                try (InputStream is = con.getInputStream()) {
                    int read;
                    byte[] bytes = new byte[1024];
                    String tempName = Long.valueOf(new Date().getTime()).toString();
                    File outputFile = new File(tempName + ".mp3");
                    outputFile.createNewFile();

                    try (OutputStream outputStream = new FileOutputStream(outputFile)) {
                        while ((read = is.read(bytes)) != -1) {
                            outputStream.write(bytes, 0, read);
                        }
                    }
                    return outputFile;
                }
            } else { // 오류 발생
                try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getErrorStream()))) {
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = br.readLine()) != null) {
                        response.append(inputLine);
                    }
                    System.out.println(response.toString());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new IOException("Error during TTS request", e);
        }
        return null;
    }
}
