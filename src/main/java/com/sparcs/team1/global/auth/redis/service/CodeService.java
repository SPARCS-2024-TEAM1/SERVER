package com.sparcs.team1.global.auth.redis.service;

import com.sparcs.team1.global.auth.redis.domain.Code;
import com.sparcs.team1.global.auth.redis.repository.CodeRepository;
import com.sparcs.team1.global.exception.enums.ErrorType;
import com.sparcs.team1.global.exception.model.CustomException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CodeService {

    private final CodeRepository codeRepository;

    public void saveVerificationCode(
            final String phoneNumber,
            final String verificationCode
    ) {
        codeRepository.save(
                Code.builder()
                        .phoneNumber(phoneNumber)
                        .verificationCode(verificationCode)
                        .build()
        );
    }

    public String findCodeByPhoneNumber(final String phoneNumber) {
        Code code = codeRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_VERIFICATION_REQUEST_HISTORY)
        );

        return code.getVerificationCode();
    }

    public void deleteVerificationCode(final String phoneNumber) {
        Code code = codeRepository.findByPhoneNumber(phoneNumber).orElseThrow(
                () -> new CustomException(ErrorType.NOT_FOUND_VERIFICATION_REQUEST_HISTORY)
        );

        codeRepository.delete(code);
    }
}
