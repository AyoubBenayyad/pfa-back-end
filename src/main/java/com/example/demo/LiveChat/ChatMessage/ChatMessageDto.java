package com.example.demo.LiveChat.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor

public class ChatMessageDto {
    String content;
    Long senderId;

    Long recipientId;



}
