package com.sparcs.team1.global.common.external.clova.chat;

import com.sparcs.team1.global.common.external.clova.summary.Status;

public record ChatResponse(
        Status status,
        Result result
) {
}
