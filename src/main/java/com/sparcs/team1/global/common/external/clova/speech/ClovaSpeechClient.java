package com.sparcs.team1.global.common.external.clova.speech;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jakarta.annotation.PostConstruct;
import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import lombok.Getter;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class ClovaSpeechClient {

    // Clova Speech secret key
    @Value("${naver.clova.speech.secret}")
    private String secret;

    // Clova Speech invoke URL
    @Value("${naver.clova.speech.url}")
    private String invokeUrl;

    private final CloseableHttpClient httpClient = HttpClients.createDefault();
    private final Gson gson = new Gson();
    private Header[] headers;

    @PostConstruct
    public void init() {
        headers = new Header[]{
                new BasicHeader("Accept", "application/json"),
                new BasicHeader("X-CLOVASPEECH-API-KEY", secret)
        };
    }

    @Getter
    public static class Diarization {
        private final Boolean enable = Boolean.FALSE;
    }

    @Getter
    public static class NestRequestEntity {
        private final String language = "ko-KR";
        private final String completion = "sync";
        private final Boolean wordAlignment = Boolean.FALSE;
        private final Boolean fullText = Boolean.TRUE;
        private final Diarization diarization = new Diarization();
    }

    public JsonObject objectStorage(String dataKey, NestRequestEntity nestRequestEntity) {
        HttpPost httpPost = new HttpPost(invokeUrl + "/recognizer/object-storage");
        httpPost.setHeaders(headers);
        Map<String, Object> body = new HashMap<>();
        body.put("dataKey", dataKey);
        body.put("language", nestRequestEntity.getLanguage());
        body.put("completion", nestRequestEntity.getCompletion());
        body.put("wordAlignment", nestRequestEntity.getWordAlignment());
        body.put("fullText", nestRequestEntity.getFullText());
        body.put("diarization", nestRequestEntity.getDiarization());
        StringEntity httpEntity = new StringEntity(gson.toJson(body), ContentType.APPLICATION_JSON);
        httpPost.setEntity(httpEntity);

        return execute(httpPost);
    }

    public JsonObject url(String url, NestRequestEntity nestRequestEntity) {
        HttpPost httpPost = new HttpPost(invokeUrl + "/recognizer/url");
        httpPost.setHeaders(headers);
        Map<String, Object> body = new HashMap<>();
        body.put("url", url);
        body.put("language", nestRequestEntity.getLanguage());
        body.put("completion", nestRequestEntity.getCompletion());
        body.put("wordAlignment", nestRequestEntity.getWordAlignment());
        body.put("fullText", nestRequestEntity.getFullText());
        body.put("diarization", nestRequestEntity.getDiarization());
        HttpEntity httpEntity = new StringEntity(gson.toJson(body), ContentType.APPLICATION_JSON);
        httpPost.setEntity(httpEntity);

        return execute(httpPost);
    }

    public JsonObject upload(File file, NestRequestEntity nestRequestEntity) {
        HttpPost httpPost = new HttpPost(invokeUrl + "/recognizer/upload");
        httpPost.setHeaders(headers);
        HttpEntity httpEntity = MultipartEntityBuilder.create()
                .addTextBody("params", gson.toJson(nestRequestEntity), ContentType.APPLICATION_JSON)
                .addBinaryBody("media", file, ContentType.MULTIPART_FORM_DATA, file.getName())
                .build();
        httpPost.setEntity(httpEntity);

        return execute(httpPost);
    }

    private JsonObject execute(HttpPost httpPost) {
        try (final CloseableHttpResponse httpResponse = httpClient.execute(httpPost)) {
            final HttpEntity entity = httpResponse.getEntity();
            String responseString = EntityUtils.toString(entity, StandardCharsets.UTF_8);
            JsonParser jsonParser = new JsonParser();

            return jsonParser.parse(responseString).getAsJsonObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
