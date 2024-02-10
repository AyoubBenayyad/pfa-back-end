package com.example.demo.Comments;


import com.example.demo.Annonce.Annonce;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;

    @ManyToOne
    @JoinColumn(name = "User_id")
    User userCommenting;

    @ManyToOne
    @JoinColumn(name = "Post_id")
    Annonce postCommented;

    String Comment_Content;
}
