package com.ssafy.questory.dto.request.chat;

import com.ssafy.questory.domain.ChatMessage;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ChatRequestDto {
    private String model;
    private List<ChatMessage> messages;
}
