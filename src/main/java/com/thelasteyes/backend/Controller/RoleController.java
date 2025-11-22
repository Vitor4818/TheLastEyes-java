package com.thelasteyes.backend.Controller;

import com.thelasteyes.backend.Dto.GetRoleDto;
import com.thelasteyes.backend.Dto.RoleFilter;
import com.thelasteyes.backend.Service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/roles")
public class RoleController {

    @Autowired
    private RoleService roleService;

    // Retorna todos os perfis
    @GetMapping
    public ResponseEntity<Page<GetRoleDto>> getAllRoles(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            RoleFilter filter) {
        return ResponseEntity.ok(roleService.getAllRoles(page, filter));
    }

    // Retorna um perfil por id
    @GetMapping("/{id}")
    public ResponseEntity<GetRoleDto> getRoleById(@PathVariable Long id) {
        return ResponseEntity.ok(roleService.getRoleById(id));
    }
}