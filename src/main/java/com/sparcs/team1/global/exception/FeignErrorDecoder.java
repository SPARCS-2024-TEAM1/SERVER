package com.sparcs.team1.global.exception;

import com.sparcs.team1.global.exception.enums.ErrorType;
import com.sparcs.team1.global.exception.model.CustomException;
import feign.Response;
import feign.codec.ErrorDecoder;
import feign.codec.StringDecoder;
import java.io.IOException;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;

@Slf4j
public class FeignErrorDecoder implements ErrorDecoder {

    private final StringDecoder stringDecoder = new StringDecoder();

    @Override
    public Exception decode(String methodKey, Response response) {

        String message;
        String error;

        if (response.body() != null) {
            try {
                String body = (String) stringDecoder.decode(response, String.class);

                log.debug(body);

                // 응답결과 JSON 파싱
                JSONObject jsonObject = new JSONObject(body);
                message = jsonObject.optString("message", "message 필드가 존재하지 않습니다.");
                error = jsonObject.optString("error", "error 필드가 존재하지 않습니다.");
            } catch (IOException | JSONException e) {
                log.error(methodKey + "Feign 요청이 실패한 후 받은 Response Body를 객체로 변환하는 과정에서 오류가 발생했습니다.", e);
                throw new CustomException(ErrorType.FAILED_REQUEST_BODY_PARSING_ERROR);
            }
        } else {
            throw new CustomException(ErrorType.NOT_FOUND_RESPONSE_BODY_ERROR);
        }

        log.error(String.valueOf(response.status()));
        log.error(message);
        log.error(error);
        log.error(String.valueOf(response.headers()));

        throw new CustomException(ErrorType.INTERNAL_FEIGN_ERROR);
    }
}
