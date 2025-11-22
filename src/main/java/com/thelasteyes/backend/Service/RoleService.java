package com.thelasteyes.backend.Service;

import com.thelasteyes.backend.Dto.GetRoleDto;
import com.thelasteyes.backend.Dto.RoleFilter;
import com.thelasteyes.backend.Exceptions.ResourceNotFoundException;
import com.thelasteyes.backend.Model.Role;
import com.thelasteyes.backend.Repository.RoleRepository;
import com.thelasteyes.backend.Specification.RoleSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleService {

    @Autowired
    private RoleRepository roleRepository;

    public Page<GetRoleDto> getAllRoles(Pageable page, RoleFilter filter) {
        Specification<Role> spec = RoleSpecification.withFilter(filter);
        return roleRepository.findAll(spec, page)
                .map(GetRoleDto::new); // 3. Mapeia o resultado para o DTO
    }

    public GetRoleDto getRoleById(Long id) {
        return roleRepository.findById(id).map(GetRoleDto::new)
                .orElseThrow(() -> new ResourceNotFoundException("Perfil (Role) com o id " + id + " não encontrado."));
    }

}