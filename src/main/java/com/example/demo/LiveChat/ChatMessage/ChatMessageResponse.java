package com.example.demo.LiveChat.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
public class ChatMessageResponse {
    Long id;
    Long senderId;
    Long recipientId;
    String content;
    Date timeStamp;


}
