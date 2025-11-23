
package com.thelasteyes.backend.Service;

import com.thelasteyes.backend.Config.RabbitMQConfig;
import com.thelasteyes.backend.Model.Checkin;
import com.thelasteyes.backend.Repository.CheckinRepository;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;

@Service
public class CheckinProducerService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private CheckinRepository checkinRepository;

    @CacheEvict(value = {"checkinResults", "checkinLatest"}, allEntries = true)
    public Long createAndSendToQueue(Long userId, String checkinText) {

        Checkin checkin = new Checkin();
        checkin.setUserId(userId);
        checkin.setCheckinText(checkinText);

        checkin = checkinRepository.save(checkin);

        rabbitTemplate.convertAndSend(
                RabbitMQConfig.QUEUE_CHECKIN_REQUEST,
                checkin.getId()
        );

        return checkin.getId();
    }
}