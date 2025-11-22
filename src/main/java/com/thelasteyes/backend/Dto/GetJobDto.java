package com.thelasteyes.backend.Dto;

import com.thelasteyes.backend.Model.Job;
import java.time.LocalDate;

public record GetJobDto(
        Long id,
        GetCompanyDto company,

        LocalDate hireDate,
        String position,
        String contractType,
        int weeklyHours,
        String workModel,
        int jobSatisfactionScore
) {
    public GetJobDto(Job job) {
        this(
                job.getId(),
                new GetCompanyDto(job.getCompany()),
                job.getHireDate(),
                job.getPosition(),
                job.getContractType(),
                job.getWeeklyHours(),
                job.getWorkModel(),
                job.getJobSatisfactionScore()
        );
    }
}