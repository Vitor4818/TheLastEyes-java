package com.thelasteyes.backend.Dto;

import java.lang.Integer;

public record JobFilter(

        String position,

        String contractType,

        String workModel,

        Long companyId,

        Integer jobSatisfactionScore
) {
}