package com.sparcs.team1.global.common.external.clova.chat;

import com.sparcs.team1.domain.mooddiary.model.Mood;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ClovaChatService {

    private final ClovaChatClient clovaChatClient;

    @Value("${naver.clova.chat.api.key}")
    private String apiKey;

    @Value("${naver.clova.chat.api.clovaStudio}")
    private String clovaStudioApiKey;

    @Value("${naver.clova.chat.api.gwApiKey}")
    private String apiGwApiKey;

    public ChatResponse sendChatRequestToDG(Mood mood, String diary) {
        ChatRequest request = new ChatRequest(
                new ChatMessage[]{
                        new ChatMessage("system",
                                "- 입시생에게 희망을 주는 어시스턴트 '동글이'입니다.\n"
                                        + "- 가장 첫 문장에 \"안녕, 나는 동글이야.\" 라고 하며 시작합니다.\n"
                                        + "- 사용자의 첫 문장 중 '기분: ' 뒤에 오는 건 사용자의 오늘 기분입니다.\n"
                                        + "- 반말로 응답합니다.\n"
                                        + "- 사용자가 하는 말에 격한 공감을 해줍니다.\n"
                                        + "- 사용자의 이야기를 긍정적으로 해석해서 말해줍니다.\n"
                                        + "- 입시생의 위치에서 생각하려고 노력합니다.\n"
                                        + "- 입시생에게 따뜻한 위로를 건넵니다.\n"
                                        + "- 입시생에게 잘 할 수 있을 거라는 희망과 응원을 건넵니다.\n"
                                        + "- 입시생에게 절대 부정적인 얘기를 하지 않습니다.\n"
                                        + "- 안될 거라는 의미를 내포한 얘기를 하지 않습니다.\n"
                                        + "- 입시생들은 빠른 시일 내에 자신이 원하는 목표를 달성하기 위해 노력하고 있으므로, 그들에게 길게 보라는 표현은 지양합니다.\n"
                                        + "- 사용자에게 부담을 주는 표현은 지양합니다."),
                        new ChatMessage("user", "기분: " + mood + "\n\n" + diary)
                },
                0.7,
                0,
                0.8,
                0.6,
                400
        );

        return clovaChatClient.sendChatRequest(apiKey, clovaStudioApiKey, apiGwApiKey, request);
    }

    public ChatResponse sendChatRequestToPJ(Mood mood, String diary) {
        ChatRequest request = new ChatRequest(
                new ChatMessage[]{
                        new ChatMessage("system",
                                "- 입시생에게 희망을 주는 어시스턴트 '동글이'입니다.\n"
                                        + "- 가장 첫 문장에 '안녕하세요, 저는 동글이입니다.' 라고 하며 시작합니다.\n"
                                        + "- 사용자의 첫 문장 중 '기분: ' 뒤에 오는 건 사용자의 오늘 기분입니다.\n"
                                        + "- 존댓말로 응답합니다.\n"
                                        + "- 사용자가 하는 말에 격한 공감을 해줍니다.\n"
                                        + "- 사용자의 이야기를 긍정적으로 해석해서 말해줍니다.\n"
                                        + "- 입시생의 위치에서 생각하려고 노력합니다.\n"
                                        + "- 입시생에게 따뜻한 위로를 건넵니다.\n"
                                        + "- 입시생에게 잘 할 수 있을 거라는 희망과 응원을 건넵니다.\n"
                                        + "- 입시생에게 절대 부정적인 얘기를 하지 않습니다.\n"
                                        + "- 안될 거라는 의미를 내포한 얘기를 하지 않습니다.\n"
                                        + "- 입시생들은 빠른 시일 내에 자신이 원하는 목표를 달성하기 위해 노력하고 있으므로, 그들에게 길게 보라는 표현은 지양합니다.\n"
                                        + "- 사용자에게 부담을 주는 표현은 지양합니다."),
                        new ChatMessage("user", "기분: " + mood + "\n\n" + diary)
                },
                0.7,
                0,
                0.8,
                0.6,
                400
        );

        return clovaChatClient.sendChatRequest(apiKey, clovaStudioApiKey, apiGwApiKey, request);
    }
}
