package com.thelasteyes.backend.Controller;

import com.thelasteyes.backend.Dto.UserFilter;
import com.thelasteyes.backend.Dto.GetUserDto;
import com.thelasteyes.backend.Dto.PostUserDto;
import com.thelasteyes.backend.Dto.PutUserDto;
import com.thelasteyes.backend.Model.User;
import com.thelasteyes.backend.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // Retorna todos os usuarios
    @GetMapping
    public ResponseEntity<Page<GetUserDto>> getAllUsers(
            @PageableDefault(size = 10, sort = "id", direction = Sort.Direction.ASC) Pageable page,
            UserFilter filter) {

        return ResponseEntity.ok(userService.getAllUsers(page, filter));
    }

    // Retorna usuario pór ID
    @GetMapping("/{id}")
    public ResponseEntity<GetUserDto> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    // Cadastra novo usuario
    @PostMapping
    public ResponseEntity<Void> createUser(@RequestBody @Valid PostUserDto dto) {
        User savedUser = userService.postUser(dto);
        return ResponseEntity.created(URI.create("/users/" + savedUser.getId())).build();
    }

    // Atualiza dados do usuario
    @PutMapping("/{id}")
    public ResponseEntity<Void> updateUser(@PathVariable Long id, @Valid @RequestBody PutUserDto dto) {
        userService.putUser(id, dto);
        return ResponseEntity.noContent().build();
    }

    // Deletar usuario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}