package com.sparcs.team1.domain.mooddiary.service;

import com.google.gson.JsonObject;
import com.sparcs.team1.domain.member.repository.MemberRepository;
import com.sparcs.team1.domain.mooddiary.dto.CreateAnswerRequest;
import com.sparcs.team1.domain.mooddiary.dto.CreateAnswerResponse;
import com.sparcs.team1.domain.mooddiary.dto.CreateDiaryRequest;
import com.sparcs.team1.domain.mooddiary.dto.CreateDiaryResponse;
import com.sparcs.team1.domain.mooddiary.model.MoodDiary;
import com.sparcs.team1.domain.mooddiary.repository.MoodDiaryRepository;
import com.sparcs.team1.global.common.external.clova.chat.ChatResponse;
import com.sparcs.team1.global.common.external.clova.chat.ClovaChatService;
import com.sparcs.team1.global.common.external.clova.speech.ClovaSpeechClient;
import com.sparcs.team1.global.common.external.clova.speech.ClovaSpeechClient.NestRequestEntity;
import com.sparcs.team1.global.common.external.clova.storage.StorageService;
import com.sparcs.team1.global.common.external.clova.summary.ClovaSummarizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MoodDiaryService {

    private final MemberRepository memberRepository;
    private final MoodDiaryRepository moodDiaryRepository;

    private final StorageService storageService;
    private final ClovaSummarizationService clovaSummarizationService;
    private final ClovaChatService clovaChatService;

    private final ClovaSpeechClient clovaSpeechClient;
    private final NestRequestEntity nestRequestEntity = new NestRequestEntity();

    @Transactional
    public CreateDiaryResponse createMoodDiary(CreateDiaryRequest createDiaryRequest) {
        MoodDiary moodDiary = MoodDiary.builder()
                .member(memberRepository.findMemberByIdOrThrow(createDiaryRequest.memberId()))
                .mood(createDiaryRequest.mood())
                .assistant(createDiaryRequest.assistant())
                .build();
        moodDiaryRepository.save(moodDiary);

        String fileName = String.valueOf(createDiaryRequest.memberId()) + moodDiary.getId() + ".mp3";

        String diary = getTextFromResponse(
                clovaSpeechClient.objectStorage(
                        storageService.uploadObjectStorage(fileName, createDiaryRequest.file()),
                        nestRequestEntity
                )
        );
        moodDiary.updateDiary(diary);
        moodDiary.updateSummary(clovaSummarizationService.summarizeTexts(new String[]{diary}).result().text());

        return CreateDiaryResponse.of(
                moodDiary.getId(),
                moodDiary.getSummary()
        );
    }

    public String getTextFromResponse(JsonObject response) {
        return response.get("text").getAsString();
    }

    @Transactional
    public CreateAnswerResponse createAnswer(CreateAnswerRequest createAnswerRequest) {
        MoodDiary moodDiary = moodDiaryRepository.findMemberByIdOrThrow(createAnswerRequest.moodDiaryId());
        String answer;

        if (moodDiary.getAssistant().getAssistant().equals("동글이")) {
            answer = getContentFromResponse(
                    clovaChatService.sendChatRequestToDG(moodDiary.getMood().getMood(), moodDiary.getDiary())
            );
        } else {
            answer = getContentFromResponse(
                    clovaChatService.sendChatRequestToPJ(moodDiary.getMood().getMood(), moodDiary.getDiary())
            );
        }
        moodDiary.updateAnswer(answer);

        return CreateAnswerResponse.of(
                moodDiary.getMember().getId(),
                moodDiary.getAnswer(),
                moodDiary.getSummary()
        );
    }

    public String getContentFromResponse(ChatResponse response) {
        return response.result().message().content();
    }
}
