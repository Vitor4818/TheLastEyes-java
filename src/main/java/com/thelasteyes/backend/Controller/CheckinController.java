package com.thelasteyes.backend.Controller;

import com.thelasteyes.backend.Dto.CheckinDto;
import com.thelasteyes.backend.Dto.CheckinResultDto;
import com.thelasteyes.backend.Model.User;
import com.thelasteyes.backend.Service.CheckinProducerService;
import com.thelasteyes.backend.Config.Security.AuthenticationService;
import com.thelasteyes.backend.Service.CheckinService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/checkin")
public class CheckinController {

    @Autowired
    private CheckinProducerService producerService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private CheckinService checkinService;

    @PostMapping
    public ResponseEntity<?> performCheckin(
            @RequestBody @Valid CheckinDto dto,
            @AuthenticationPrincipal UserDetails userDetails
    ) {

        String userEmail = userDetails.getUsername();
        Long userId = dto.userId();
        Long checkinId = producerService.createAndSendToQueue(userId, dto.text());
        return ResponseEntity.accepted().body(
                "Pedido de classificação aceito. O resultado será processado e estará disponível em breve. " +
                        "ID do Checkin: " + checkinId
        );
    }

    @GetMapping("/results")
    public ResponseEntity<Page<CheckinResultDto>> getResultsByUserId(Pageable pageable) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User userEntity = authenticationService.findUserEntityByEmail(userEmail);
        Long userId = userEntity.getId();
        Page<CheckinResultDto> results = checkinService.getResultsByUserId(userId, pageable);
        return ResponseEntity.ok(results);
    }

    @GetMapping("/latest-result")
    public ResponseEntity<CheckinResultDto> getLastResultByUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName();
        User userEntity = authenticationService.findUserEntityByEmail(userEmail);
        Long userId = userEntity.getId();
        CheckinResultDto latestResult = checkinService.getLastResultByUserId(userId);
        return ResponseEntity.ok(latestResult);
    }
}