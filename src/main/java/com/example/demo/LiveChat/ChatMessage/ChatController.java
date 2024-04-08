package com.example.demo.LiveChat.ChatMessage;


import com.example.demo.LiveChat.ChatRoom.ChatRoomService;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {

    private final ChatMessageService chatMessageService;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;


    @MessageMapping("/chat")
    public void ProcessMessage(@Payload ChatMessageDto chatMessageDto){

        User sender =  userRepository.findById(chatMessageDto.senderId).orElseThrow();
        User recipient =  userRepository.findById(chatMessageDto.recipientId).orElseThrow();
        ChatMessage message=ChatMessage.builder()
                .sender(sender)
                .recipient(recipient)
                .content(chatMessageDto.getContent())
                .timeStamp(new Date())
                .build();
        chatMessageService.save(message);

        messagingTemplate.convertAndSendToUser(
                String.valueOf(recipient.getId()),  "/queue/messages", new ChatNotification(message.getId(),
                message.getSender().getId(),
                message.getRecipient().getId(),
                message.getContent()));
    }

    @MessageMapping("/send/message")
    @SendTo("/topic/public")
    public ChatNotification handleMessage( @Payload ChatMessageDto chatMessageDto) {
        User sender =  userRepository.findById(chatMessageDto.senderId).orElseThrow();
        User recipient =  userRepository.findById(chatMessageDto.recipientId).orElseThrow();
        ChatMessage message=ChatMessage.builder()
                .sender(sender)
                .recipient(recipient)
                .content(chatMessageDto.getContent())
                .timeStamp(new Date())
                .build();

        return new  ChatNotification(message.getId(),
                        message.getSender().getId(),
                        message.getRecipient().getId(),
                        message.getContent());
    }
    @GetMapping("/api/v1/messages/{senderId},{recipientId}")
    public ResponseEntity<List<ChatMessageResponse>> findChatMessages(@PathVariable Long senderId,@PathVariable Long recipientId) throws Exception {
        try{
            User sender =  userRepository.findById(senderId).orElseThrow();
            User recipient =  userRepository.findById(recipientId).orElseThrow();

            return ResponseEntity.ok(chatMessageService.findChatMessages(sender,recipient));
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }

    }
}
