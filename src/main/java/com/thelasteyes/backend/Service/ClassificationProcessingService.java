package com.thelasteyes.backend.Service;

import com.thelasteyes.backend.Dto.ClassificationResponseMessage;
import com.thelasteyes.backend.Model.Checkin;
import com.thelasteyes.backend.Model.ClassificationStatus;
import com.thelasteyes.backend.Repository.CheckinRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ClassificationProcessingService {

    @Autowired
    private CheckinRepository checkinRepository;

    @Autowired
    private ClassificationApiGateway apiGateway;

    @Transactional
    public void processAndSaveClassification(Long checkinId) {

        Checkin checkin = checkinRepository.findById(checkinId)
                .orElseThrow(() -> new RuntimeException("Check-in ID " + checkinId + " não encontrado."));

        ClassificationResponseMessage response;

        try {
            Long userId = checkin.getUserId();
            String checkinText = checkin.getCheckinText();

            response = apiGateway.classify(userId, checkinText);

            checkin.setSentimentResult(response.sentiment());
            checkin.setClassificationScore(response.score());
            checkin.setStatus(ClassificationStatus.PROCESSED);

        } catch (Exception e) {
            checkin.setStatus(ClassificationStatus.FAILED);
            checkinRepository.save(checkin);

            throw new RuntimeException("Falha ao chamar a API de classificação ou erro de processamento: " + e.getMessage(), e);
        }

        checkinRepository.save(checkin);
    }
}