package com.thelasteyes.backend.Worker;

import com.thelasteyes.backend.Config.RabbitMQConfig;
import com.thelasteyes.backend.Service.ClassificationProcessingService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CheckinWorkerConsumer {

    private static final Logger log = LoggerFactory.getLogger(CheckinWorkerConsumer.class);

    @Autowired
    private ClassificationProcessingService processingService;

    @RabbitListener(queues = RabbitMQConfig.QUEUE_CHECKIN_REQUEST)
    public void listenCheckinRequest(Long checkinId) {
        log.info("Mensagem recebida da fila. Check-in ID para processamento: {}", checkinId);

        try {
            processingService.processAndSaveClassification(checkinId);

            log.info("Processamento concluído e resultado salvo para o Check-in ID: {}", checkinId);

        } catch (Exception e) {
            log.error("Falha ao processar classificação para o Check-in ID: {}. Erro: {}",
                    checkinId, e.getMessage());
        }
    }
}