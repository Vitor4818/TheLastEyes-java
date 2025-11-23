package com.thelasteyes.backend.Service;

import com.thelasteyes.backend.Dto.ClassificationResponseMessage;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@Service
public class ClassificationApiGateway {

    @Value("${api.classification.url}")
    private String classificationApiUrl;

    private final RestTemplate restTemplate;

    public ClassificationApiGateway(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ClassificationResponseMessage classify(Long userId, String text) {

        String url = classificationApiUrl + "/analyze";

        Map<String, Object> requestBody = Map.of(
                "text", text,
                "userId", userId
        );

        try {
            return restTemplate.postForObject(
                    url,
                    requestBody,
                    ClassificationResponseMessage.class
            );

        } catch (Exception e) {
            throw new RuntimeException("Falha ao comunicar com API Python: " + e.getMessage(), e);
        }
    }
}
