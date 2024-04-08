package com.example.demo.LiveChat.ChatRoom;

import com.example.demo.LiveChat.ChatMessage.ChatMessage;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String ChatId;
    @ManyToOne
    private User sender;

    @ManyToOne
    private User recipient;

    @JsonManagedReference
    @OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
    private List<ChatMessage> chatMessages; // Bidirectional relationship with ChatMessage
}
