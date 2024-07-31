package com.sparcs.team1.domain.mooddiary.dto;

import java.io.File;

public record CreateAudioResponse(
        File audioFile
) {
    public static CreateAudioResponse of(
            final File audioFile
    ) {
        return new CreateAudioResponse(
                audioFile
        );
    }
}
