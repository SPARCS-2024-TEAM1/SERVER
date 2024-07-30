package com.sparcs.team1.domain.member.service;

import com.sparcs.team1.domain.member.dto.OnboardingResponse;
import com.sparcs.team1.domain.member.dto.SendCodeRequest;
import com.sparcs.team1.domain.member.dto.SignUpRequest;
import com.sparcs.team1.domain.member.dto.VerifyCodeRequest;
import com.sparcs.team1.domain.member.model.Member;
import com.sparcs.team1.domain.member.repository.MemberRepository;
import com.sparcs.team1.global.auth.redis.service.CodeService;
import com.sparcs.team1.global.exception.enums.ErrorType;
import com.sparcs.team1.global.exception.model.CustomException;
import jakarta.annotation.PostConstruct;
import java.util.Random;
import lombok.RequiredArgsConstructor;
import net.nurigo.sdk.NurigoApp;
import net.nurigo.sdk.message.model.Message;
import net.nurigo.sdk.message.request.SingleMessageSendingRequest;
import net.nurigo.sdk.message.service.DefaultMessageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    private DefaultMessageService defaultMessageService;
    private final CodeService codeService;

    @Value("${coolsms.api.key}")
    private String apiKey;

    @Value("${coolsms.api.secret}")
    private String apiSecret;

    @Value("${coolsms.fromNumber}")
    private String fromNumber;

    private static final String PHONE_NUMBER_PATTERN = "^010\\d{8}$";
    private static final String NICKNAME_PATTERN = "^[a-zA-Z가-힣]{1,10}$";

    @PostConstruct
    public void init() {
        this.defaultMessageService = NurigoApp.INSTANCE.initialize(apiKey, apiSecret, "https://api.coolsms.co.kr");
    }

    // 인증번호 문자 발송
    @Transactional
    public void sendCode(final SendCodeRequest sendCodeRequest) {
        Message message = new Message();

        String toNumber = validPhoneNumber(sendCodeRequest.phoneNumber());

        message.setFrom(fromNumber);
        message.setTo(toNumber);

        String verificationCode = generateRandomNumber(6);
        message.setText("[AREA] 인증번호는 [" + verificationCode + "] 입니다.");

        this.defaultMessageService.sendOne(new SingleMessageSendingRequest(message));
        codeService.saveVerificationCode(
                toNumber,
                verificationCode
        );
    }

    // 전화번호 유효성 검증
    private String validPhoneNumber(String phoneNumber) {
        // 발신번호 및 수신번호는 반드시 01012345678 형태로 입력되어야 함.
        phoneNumber = phoneNumber.replaceAll("-", "");

        if (!phoneNumber.matches(PHONE_NUMBER_PATTERN)) {
            throw new CustomException(ErrorType.INVALID_PHONE_NUMBER_ERROR);
        }

        return phoneNumber;
    }

    // 인증번호를 위한 랜덤 숫자 생성
    private String generateRandomNumber(final int digitCount) {
        Random random = new Random();
        int min = (int) Math.pow(10, digitCount - 1);
        int max = (int) Math.pow(10, digitCount) - 1;

        return String.valueOf(random.nextInt((max - min) + 1) + min);
    }

    // 인증번호 일치 여부 확인
    @Transactional
    public void verifyCode(final VerifyCodeRequest verifyCodeRequest) {
        String phoneNumber = validPhoneNumber(verifyCodeRequest.phoneNumber());

        if (verifyCodeRequest.verificationCode().equals(codeService.findCodeByPhoneNumber(phoneNumber))) {
            codeService.deleteVerificationCode(phoneNumber);
        } else {
            throw new CustomException(ErrorType.INVALID_VERIFICATION_CODE_ERROR);
        }
    }

    @Transactional(readOnly = true)
    public OnboardingResponse signIn(String phoneNumber) {
        phoneNumber = phoneNumber.replaceAll("-", "");

        if (isExistingMember(phoneNumber)) {
            Member member = memberRepository.findMemberByPhoneNumberOrThrow(phoneNumber);

            return OnboardingResponse.of(
                    member.getId(),
                    member.getNickname()
            );
        } else {
            return null;
        }
    }

    private boolean isExistingMember(final String phoneNumber) {
        return memberRepository.findMemberByPhoneNumber(phoneNumber).isPresent();
    }

    @Transactional
    public OnboardingResponse signUp(final SignUpRequest signUpRequest) {
        validNickname(signUpRequest.nickname());
        String phoneNumber = validPhoneNumber(signUpRequest.phoneNumber());

        if (!phoneNumber.matches(PHONE_NUMBER_PATTERN)) {
            throw new CustomException(ErrorType.INVALID_PHONE_NUMBER_ERROR);
        }

        Member member = Member.builder()
                .phoneNumber(signUpRequest.phoneNumber())
                .nickname(signUpRequest.nickname())
                .build();
        memberRepository.save(member);

        return OnboardingResponse.of(
                member.getId(),
                member.getNickname()
        );
    }

    // 닉네임 유효성 검증
    private void validNickname(final String nickname) {
        // 형식 체크
        if (!nickname.matches(NICKNAME_PATTERN)) {
            throw new CustomException(ErrorType.INVALID_NICKNAME_ERROR);
        }
    }
}
