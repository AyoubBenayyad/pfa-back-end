package com.example.demo.LiveChat.ChatRoom;

import com.example.demo.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatRoomRepository  extends JpaRepository<ChatRoom,Long> {



    @Query("SELECT c FROM ChatRoom c WHERE c.sender.id = ?1 AND c.recipient.id = ?2")
    Optional<ChatRoom> findBySenderIdAndRecipientId(Long senderId, Long recipientId);

}
