package com.thelasteyes.backend.Dto;

import com.thelasteyes.backend.Model.Checkin;

public record CheckinResultDto(
        Long id,
        String checkinText,
        String suggestionText // A resposta do Gemini
) {
    public CheckinResultDto(Checkin checkin) {
        this(
                checkin.getId(),
                checkin.getCheckinText(),
                checkin.getSuggestionText()
        );
    }
}