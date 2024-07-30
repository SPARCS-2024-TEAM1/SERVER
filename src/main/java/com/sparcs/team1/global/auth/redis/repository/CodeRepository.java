package com.sparcs.team1.global.auth.redis.repository;

import com.sparcs.team1.global.auth.redis.domain.Code;
import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface CodeRepository extends CrudRepository<Code, String> {

    Optional<Code> findByPhoneNumber(final String phoneNumber);
}
