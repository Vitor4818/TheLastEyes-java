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

    @Autowired
    private SuggestionService suggestionService;

    @Transactional
    public void processAndSaveClassification(Long checkinId) {

        Checkin checkin = checkinRepository.findById(checkinId)
                .orElseThrow(() -> new RuntimeException("Check-in ID " + checkinId + " não encontrado."));

        String sentimentContext = "NEUTRO";
        Double scoreContext = 0.5;
        boolean pythonSuccess = false;
        try {
            Long userId = checkin.getUserId();
            String checkinText = checkin.getCheckinText();

            ClassificationResponseMessage response = apiGateway.classify(userId, checkinText);
            sentimentContext = response.sentiment();
            scoreContext = response.score();

            checkin.setSentimentResult(sentimentContext);
            checkin.setClassificationScore(scoreContext);
            checkin.setStatus(ClassificationStatus.PROCESSED);
            pythonSuccess = true;

        } catch (Exception e) {
            checkin.setStatus(ClassificationStatus.FAILED);
            checkinRepository.save(checkin);
        }

        try {
            String suggestion = suggestionService.generateHobbySuggestion(
                    sentimentContext,
                    checkin.getCheckinText()
            );
            checkin.setSuggestionText(suggestion);

        } catch (Exception e) {
            checkin.setSuggestionText("[ERRO IA] Falha ao gerar sugestão. Motivo: " + e.getMessage());
        }
        checkinRepository.save(checkin);

    }
}