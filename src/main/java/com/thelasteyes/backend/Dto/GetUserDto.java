package com.thelasteyes.backend.Dto;

import com.thelasteyes.backend.Model.User;
import com.thelasteyes.backend.Model.Job;
import java.time.LocalDate;

public record GetUserDto(
        Long id,
        String name,
        String email,
        String cpf,
        String phone,
        LocalDate birthDate,
        GetRoleDto role,
        GetJobDto currentJob
) {

    public GetUserDto(User user, Job currentJob) {
        this(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getCpf(),
                user.getPhone(),
                user.getBirthDate(),

                new GetRoleDto(user.getRole()),
                currentJob != null ? new GetJobDto(currentJob) : null
        );
    }
}