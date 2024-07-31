package com.sparcs.team1.domain.mooddiary.service;

import com.google.gson.JsonObject;
import com.sparcs.team1.domain.member.model.Member;
import com.sparcs.team1.domain.member.repository.MemberRepository;
import com.sparcs.team1.domain.mooddiary.dto.CreateAnswerRequest;
import com.sparcs.team1.domain.mooddiary.dto.CreateAnswerResponse;
import com.sparcs.team1.domain.mooddiary.dto.CreateAudioRequest;
import com.sparcs.team1.domain.mooddiary.dto.CreateAudioResponse;
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
import com.sparcs.team1.global.common.external.clova.tts.NaverTtsService;
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
    private final NaverTtsService naverTtsService;

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
        MoodDiary moodDiary = moodDiaryRepository.findMoodDiaryByIdOrThrow(createAnswerRequest.moodDiaryId());
        Member member = moodDiary.getMember();
        String answer;

        if (moodDiary.getAssistant().name().equals("동글이")) {
            answer = getContentFromResponse(
                    clovaChatService.sendChatRequestToDG(moodDiary.getMood().name(), moodDiary.getDiary())
            );

            if (member.getNickname() != null) {
                answer = answer.replace("사용자", member.getNickname());
            }
        } else {
            answer = getContentFromResponse(
                    clovaChatService.sendChatRequestToPJ(moodDiary.getMood().name(), moodDiary.getDiary())
            );

            if (member.getNickname() != null) {
                answer = answer.replace("사용자", member.getNickname());
            }
        }
        moodDiary.updateAnswer(answer);

        return CreateAnswerResponse.of(
                member.getId(),
                moodDiary.getAnswer(),
                moodDiary.getSummary()
        );
    }

    public String getContentFromResponse(ChatResponse response) {
        return response.result().message().content();
    }

    public CreateAudioResponse createAudio(CreateAudioRequest createAudioRequest) {
        MoodDiary moodDiary = moodDiaryRepository.findMoodDiaryByIdOrThrow(createAudioRequest.moodDiaryId());
        if (moodDiary.getAssistant().name().equals("동글이")) {
            return CreateAudioResponse.of(
                    naverTtsService.convertTextToSpeechDG(moodDiary.getAnswer())
            );
        } else {
            return CreateAudioResponse.of(
                    naverTtsService.convertTextToSpeechPJ(moodDiary.getAnswer())
            );
        }
    }
}
