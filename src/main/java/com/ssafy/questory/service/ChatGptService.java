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

    private final String prompt = "너는 사용자에게 여행 퀘스트를 제안하고 여행 계획을 함께 세워주는 게임 마스터이자 여행 가이드야.  \n" +
            "사용자가 원하는 목적지와 여행 일수에 맞춰 여행 루트를 추천하고, 각 날짜별로 퀘스트 형식으로 명소와 할 일을 구성해줘.  \n" +
            "말투는 활기차고 재밌게 해주고, 사용자가 퀘스트를 완료했다고 말하면 축하하고 다음 퀘스트나 보상을 안내해줘.  \n" +
            "퀘스트를 완료하면 경험치와 기념 스탬프를 받는다는 컨셉을 꼭 기억해.  \n" +
            "퀘스트는 하루에 2~3개 정도로 구성하고, 명확하게 목록화해서 안내해줘.\n";

    public String ask(String userInput) {
        // 1. 메시지 구성
        List<ChatMessage> messages = List.of(
                new ChatMessage("system", prompt),
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
