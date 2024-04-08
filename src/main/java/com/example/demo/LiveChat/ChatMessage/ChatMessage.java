package com.example.demo.LiveChat.ChatMessage;

import com.example.demo.LiveChat.ChatRoom.ChatRoom;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class ChatMessage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @JsonBackReference
    @ManyToOne
    private ChatRoom chatRoom;

    @ManyToOne
    private User sender;
    @ManyToOne
    private User recipient;

    private String content;

    private Date timeStamp;
}
