package com.example.demo.Rating;

import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class UserRating {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "ratingUser_id")
    private User ratingUser;

    @ManyToOne
    @JoinColumn(name = "ratedUser_id")
    private User ratedUser;

    @Enumerated(EnumType.STRING)
    private StarRating rating;

    public UserRating(User ratingUser, User ratedUser,StarRating rating){
        this.ratingUser = ratingUser;
        this.ratedUser = ratedUser;
        this.rating = rating;
    }

}