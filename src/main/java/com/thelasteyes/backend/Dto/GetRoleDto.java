package com.thelasteyes.backend.Dto;

import com.thelasteyes.backend.Model.Role;

public record GetRoleDto(
        Long id,
        String name,
        String description
) {
    public GetRoleDto(Role role) {
        this(
                role.getId(),
                role.getName(),
                role.getDescription()
        );
    }
}