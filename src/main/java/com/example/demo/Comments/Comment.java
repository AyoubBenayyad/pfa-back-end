package com.example.demo.Comments;


import com.example.demo.Annonce.Annonce;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.Date;
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
    @JsonBackReference
    User userCommenting;

    @ManyToOne
    @JoinColumn(name = "Post_id")
    @JsonBackReference
    Annonce postCommented;

    @Temporal(TemporalType.TIMESTAMP)
    private Date postedAt;

    String Comment_Content;
}
