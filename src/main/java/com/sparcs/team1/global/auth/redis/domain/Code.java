package com.sparcs.team1.global.auth.redis.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "VerificationCode", timeToLive = 3 * 60L) // TTL 3분
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Code {

    @Id
    private String phoneNumber;

    private String verificationCode;

    @Builder
    private Code(
            final String phoneNumber,
            final String verificationCode
    ) {
        this.phoneNumber = phoneNumber;
        this.verificationCode = verificationCode;
    }
}
