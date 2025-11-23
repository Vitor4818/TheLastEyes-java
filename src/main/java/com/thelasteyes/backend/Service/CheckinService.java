package com.thelasteyes.backend.Service;

import com.thelasteyes.backend.Dto.CheckinResultDto;
import com.thelasteyes.backend.Exceptions.ResourceNotFoundException;
import com.thelasteyes.backend.Model.Checkin;
import com.thelasteyes.backend.Model.User;
import com.thelasteyes.backend.Repository.CheckinRepository;
import com.thelasteyes.backend.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CheckinService {

    @Autowired
    private CheckinRepository checkinRepository;
    @Autowired
    private UserRepository userRepository;

    @Cacheable(value = "checkinResults", key = "#userId + #pageable.pageNumber")
    public Page<CheckinResultDto> getResultsByUserId(Long userId, Pageable pageable) {
        userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("Usuário com id " + userId + " não encontrado"));
        Page<Checkin> checkinsPage = checkinRepository.findAllByUserId(userId, pageable);
        return checkinsPage.map(CheckinResultDto::new);
    }
    @Cacheable(value = "checkinLatest", key = "#userId")
    public CheckinResultDto getLastResultByUserId(Long userId) {
        userRepository.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("Usuário com id " + userId + " não encontrado"));
        Optional<Checkin> latestCheckin = checkinRepository.findFirstByUserIdOrderByIdDesc(userId);
        return latestCheckin
                .map(CheckinResultDto::new)
                .orElseThrow(() -> new ResourceNotFoundException("Nenhum check-in encontrado para o usuário " + userId));
    }
}