package com.example.demo.LiveChat.ChatMessage;

import com.example.demo.LiveChat.ChatRoom.ChatRoom;
import com.example.demo.LiveChat.ChatRoom.ChatRoomRepository;
import com.example.demo.LiveChat.ChatRoom.ChatRoomService;
import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository chatMessageRepository;
    private final ChatRoomRepository chatRoomRepository;
    private final ChatRoomService chatRoomService;


    public ChatMessage save(ChatMessage chatMessage){
        var ChatRoom=chatRoomService.getChatRoom(chatMessage.getSender(),chatMessage.getRecipient(),true)
                .orElseThrow();
        chatMessage.setChatRoom(ChatRoom);

        return chatMessageRepository.save(chatMessage);
    }

    public List<ChatMessageResponse> findChatMessages(User sender, User recipient){
        Optional<ChatRoom> chatRoom = chatRoomService.getChatRoom(sender, recipient, false);

        if (chatRoom.isPresent()) {
            List<ChatMessage> chatMessages = chatMessageRepository.findChatMessagesNative(sender.getId(),recipient.getId());
            List<ChatMessageResponse>response=new ArrayList<>();
            for ( ChatMessage message : chatMessages) {
                response.add(ChatMessageResponse.builder()
                                .id(message.getId())
                                .recipientId(message.getRecipient().getId())
                                .senderId(message.getSender().getId())
                                .content(message.getContent())
                                .timeStamp(message.getTimeStamp())
                        .build());

            }

            return response;
        } else {
            return new ArrayList<>();
        }

    }
}
