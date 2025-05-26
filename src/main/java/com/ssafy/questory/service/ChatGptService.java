package com.ssafy.questory.service;

import com.ssafy.questory.config.OpenAiProperties;
import com.ssafy.questory.domain.ChatMessage;
import com.ssafy.questory.dto.request.chat.ChatRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatGptService {
    private final OpenAiProperties openAiProperties;
    private final RestTemplate restTemplate = new RestTemplate();

    public String ask(String userInput) {
        // 1. 메시지 구성
        List<ChatMessage> messages = List.of(
                new ChatMessage("user", userInput)
        );

        // 2. 요청 생성
        ChatRequestDto chatRequest = new ChatRequestDto(openAiProperties.getModel(), messages);

        // 3. 헤더 세팅
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openAiProperties.getApiKey());

        // 4. 요청 보내기
        HttpEntity<ChatRequestDto> requestEntity = new HttpEntity<>(chatRequest, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(
                openAiProperties.getUrl(),
                requestEntity,
                Map.class
        );

        // 5. 응답 파싱
        List<Map<String, Object>> choices = (List<Map<String, Object>>) response.getBody().get("choices");
        Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
        return (String) message.get("content");
    }
}
