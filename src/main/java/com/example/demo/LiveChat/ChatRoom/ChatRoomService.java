package com.example.demo.LiveChat.ChatRoom;

import com.example.demo.user.User;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor

public class ChatRoomService {

    private final ChatRoomRepository chatRoomRepository;
    public Optional<ChatRoom>getChatRoom(User sender, User recipient, boolean CreateNewRoomIfDoesntExist){

        return chatRoomRepository.findBySenderIdAndRecipientId(sender.getId(),recipient.getId())

                .or(()->{

                    if(CreateNewRoomIfDoesntExist){
                        var chatRoom= createChatId(sender,recipient);
                        return Optional.of(chatRoom);
                    }

                    return Optional.empty();
                });
    }

    private ChatRoom createChatId(User sender, User recipient) {

        String chatId = String.format("%d_%d", sender.getId(), recipient.getId());

        ChatRoom senderRecipient = ChatRoom.builder()
                .ChatId(chatId)
                .sender(sender)
                .recipient(recipient)
                .build();

        ChatRoom recipientSender = ChatRoom.builder()
                .ChatId(chatId)
                .sender(recipient)
                .recipient(sender)
                .build();
       chatRoomRepository.save(senderRecipient);
       chatRoomRepository.save(recipientSender);
       return senderRecipient;
    }

}
