package com.thelasteyes.backend.Controller;

import com.thelasteyes.backend.Dto.CheckinDto;
import com.thelasteyes.backend.Model.User;
import com.thelasteyes.backend.Service.CheckinProducerService;
import com.thelasteyes.backend.Config.Security.AuthenticationService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/checkin")
public class CheckinController {

    @Autowired
    private CheckinProducerService producerService;

    @Autowired
    private AuthenticationService authenticationService;

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
}