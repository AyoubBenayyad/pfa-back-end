package com.example.demo.Vote;

import com.example.demo.Annonce.Annonce;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Vote {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne
    @JsonBackReference

    private User user;

    @ManyToOne
    @JsonBackReference
    private Annonce post;

    @Enumerated(EnumType.STRING)
    Type_Vote type;


    public Vote(User user, Annonce annonce, Type_Vote typeVote) {
        this.user=user;
        this.post=annonce;
        this.type=typeVote;
    }
}
