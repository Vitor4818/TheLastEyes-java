package com.thelasteyes.backend.Dto;


public record CheckinRequestMessage(

        Long userId,
        String text

) { }