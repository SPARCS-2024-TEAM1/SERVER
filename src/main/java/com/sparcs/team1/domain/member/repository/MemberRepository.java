package com.sparcs.team1.domain.member.repository;

import com.sparcs.team1.domain.member.model.Member;
import com.sparcs.team1.global.exception.enums.ErrorType;
import com.sparcs.team1.global.exception.model.CustomException;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Optional<Member> findMemberByPhoneNumber(String phoneNumber);

    default Member findMemberByPhoneNumberOrThrow(String phoneNumber) {
        return findMemberByPhoneNumber(phoneNumber)
                .orElseThrow(() -> new CustomException(ErrorType.NOT_FOUND_MEMBER_ERROR));
    }
}
