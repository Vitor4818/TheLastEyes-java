package com.thelasteyes.backend.Dto;


public record ClassificationResponseMessage(

        Long userId,
        String text,
        String sentiment,
        Double score

) { }