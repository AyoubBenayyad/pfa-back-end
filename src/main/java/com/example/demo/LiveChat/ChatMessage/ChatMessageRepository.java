package com.example.demo.LiveChat.ChatMessage;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage,Long> {

    @Query(value = "SELECT * FROM chat_message cm WHERE (cm.sender_id = :senderId OR cm.recipient_id = :senderId) AND (cm.sender_id = :recipientId OR cm.recipient_id = :recipientId) ORDER BY  cm.time_stamp", nativeQuery = true)
    List<ChatMessage> findChatMessagesNative(@Param("senderId") Long senderId, @Param("recipientId") Long recipientId);


    @Query("SELECT DISTINCT u FROM User u " +
            "JOIN ChatMessage cm ON u = cm.sender OR u = cm.recipient " +
            "WHERE u.id <> :userId AND (cm.sender.id = :userId OR cm.recipient.id = :userId)")
    List<User> findConversationUsers(@Param("userId")Long userId);
}
