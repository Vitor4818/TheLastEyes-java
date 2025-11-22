package com.thelasteyes.backend.Service;

import com.thelasteyes.backend.Dto.UserFilter;
import com.thelasteyes.backend.Dto.GetUserDto;
import com.thelasteyes.backend.Dto.PostUserDto;
import com.thelasteyes.backend.Dto.PutUserDto;
import com.thelasteyes.backend.Exceptions.DataConflictException;
import com.thelasteyes.backend.Exceptions.ResourceNotFoundException;
import com.thelasteyes.backend.Model.User;
import com.thelasteyes.backend.Model.Role;
import com.thelasteyes.backend.Model.Job;
import com.thelasteyes.backend.Repository.UserRepository;
import com.thelasteyes.backend.Repository.RoleRepository;
import com.thelasteyes.backend.Repository.JobRepository;
import com.thelasteyes.backend.Specification.UserSpecification;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private JobRepository jobRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;



    // Retorna todos os usuários
    public Page<GetUserDto> getAllUsers(Pageable page, UserFilter filter) {
        Specification<User> spec = UserSpecification.withFilter(filter);
        return userRepository.findAll(spec, page)
                .map(user -> new GetUserDto(user, user.getCurrentJob()));
    }

    // Retorna usuário por ID
    public GetUserDto getUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com o id " + id + " não encontrado"));
        return new GetUserDto(user, user.getCurrentJob());
    }


    // Cadastra um novo usuário
    @Transactional
    public User postUser(PostUserDto dto) {
        if (userRepository.existsByEmail(dto.email())) {
            throw new DataConflictException("Esse email já está em uso");
        }
        if (userRepository.existsByCpf(dto.cpf())) {
            throw new DataConflictException("Esse CPF já está em uso");
        }
        Role role = roleRepository.findById(dto.roleId())
                .orElseThrow(() -> new ResourceNotFoundException("Perfil com o id " + dto.roleId() + " não encontrado para vincular."));

        Job currentJob = jobRepository.findById(dto.currentJobId())
                .orElseThrow(() -> new ResourceNotFoundException("Emprego com o id " + dto.currentJobId() + " não encontrado para vincular."));

        if (currentJob.getUser() != null) {
            throw new DataConflictException("O Job (registro de trabalho) com o id " + dto.currentJobId() + " já está vinculado a outro usuário e não pode ser reusado como 'currentJob'.");
        }

        User user = new User();
        user.setName(dto.name());
        user.setEmail(dto.email());
        user.setPassword(passwordEncoder.encode(dto.password()));
        user.setCpf(dto.cpf());
        user.setPhone(dto.phone());
        user.setBirthDate(dto.birthDate());
        user.setRole(role);
        user.setCurrentJob(currentJob);

        return userRepository.save(user);
    }


    // Atualiza dados do usuário
    @Transactional
    public GetUserDto putUser(Long id, PutUserDto dto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com o id " + id + " não encontrado"));

        if (dto.email() != null && !dto.email().equals(user.getEmail())) {
            if (userRepository.existsByEmailAndIdNot(dto.email(), id)) {
                throw new DataConflictException("Não foi possível atualizar dados do usuário. O email digitado já está em uso.");
            }
        }

        if (dto.cpf() != null && !dto.cpf().equals(user.getCpf())) {
            if (userRepository.existsByCpfAndIdNot(dto.cpf(), id)) {
                throw new DataConflictException("Não foi possível atualizar dados do usuário. O CPF digitado já está em uso.");
            }
        }

        if (dto.roleId() != null && (user.getRole() == null || !dto.roleId().equals(user.getRole().getId()))) {
            Role role = roleRepository.findById(dto.roleId())
                    .orElseThrow(() -> new ResourceNotFoundException("Novo Perfil com o id " + dto.roleId() + " não encontrado."));
            user.setRole(role);
        }

        if (dto.currentJobId() != null) {
            Job newCurrentJob = jobRepository.findById(dto.currentJobId())
                    .orElseThrow(() -> new ResourceNotFoundException("Job com o id " + dto.currentJobId() + " não encontrado para vincular."));

            if (newCurrentJob.getUser() != null && newCurrentJob.getUser().getId() != user.getId()) {
                throw new DataConflictException("O Job ID " + dto.currentJobId() + " já está vinculado a outro usuário");
            }

            if (dto.password() != null) {
                user.setPassword(passwordEncoder.encode(dto.password()));
            }

            user.setCurrentJob(newCurrentJob);
            newCurrentJob.setUser(user);
            jobRepository.save(newCurrentJob);
        }
        user.updateData(dto);
        User updatedUser = userRepository.save(user);
        return new GetUserDto(updatedUser, updatedUser.getCurrentJob());
    }

    // Deleta dados do usuário
    @Transactional
    public void deleteUser(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuário com o id " + id + " não encontrado"));
        userRepository.delete(user);
    }
}