package com.sparcs.team1.domain.member.controller;

import com.sparcs.team1.domain.member.dto.OnboardingResponse;
import com.sparcs.team1.domain.member.dto.SendCodeRequest;
import com.sparcs.team1.domain.member.dto.SignUpRequest;
import com.sparcs.team1.domain.member.dto.VerifyCodeRequest;
import com.sparcs.team1.domain.member.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("/phone/code")
    public ResponseEntity<Void> sendCode(
            @Valid @RequestBody SendCodeRequest sendCodeRequest
    ) {
        memberService.sendCode(sendCodeRequest);

        return ResponseEntity.ok().build();
    }

    @PostMapping("/phone/verification")
    public ResponseEntity<OnboardingResponse> verifyCode(
            @Valid @RequestBody VerifyCodeRequest verifyCodeRequest
    ) {
        memberService.verifyCode(verifyCodeRequest);

        return ResponseEntity.ok(memberService.signIn(verifyCodeRequest.phoneNumber()));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<OnboardingResponse> signUp(
            @Valid @RequestBody SignUpRequest signUpRequest
    ) {
        return ResponseEntity.ok(memberService.signUp(signUpRequest));
    }
}
