package com.thelasteyes.backend.Service;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.stereotype.Service;

@Service
public class SuggestionService {

    private final ChatClient chatClient;

    public SuggestionService(ChatClient.Builder chatClientBuilder) {
        this.chatClient = chatClientBuilder.build();
    }

    public String generateHobbySuggestion(String sentiment, String checkinText) {

        String prompt = String.format(
                "O sentimento principal do check-in é '%s'. A resposta do usuário foi: '%s'. " +
                        "Com base no humor do usuário, gere uma sugestão de 50 palavras sobre como preservar sua saúde mental. " +
                        "Se o sentimento for negativo, como estresse, ansiedade, tristeza ou frustração, sugira hobbies e atividades que ajudem a relaxar e aliviar a tensão. " +
                        "Responda em Português de forma acolhedora e motivadora.",
                sentiment, checkinText
        );

        return chatClient.prompt()
                .user(prompt)
                .call()
                .content();
    }
}