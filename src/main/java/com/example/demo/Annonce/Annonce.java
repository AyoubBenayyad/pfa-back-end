package com.example.demo.Annonce;


import com.example.demo.Comments.Comment;
import com.example.demo.user.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Annonce {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long Id;
    String Title;
    String Description;
    @Temporal(TemporalType.DATE)
    Date publicationDate;
    int mark = 0;
    @ManyToOne
    @JoinColumn(name = "Posting_User")
    User userPosting;

    @OneToMany(mappedBy = "postCommented")
    Set<Comment> comments = new HashSet<>();

    @OneToMany(mappedBy = "image")
    List<Photos> photos = new ArrayList<>();

    public Annonce(String title, String description, Date publicationDate) {
        Title = title;
        Description = description;
        this.publicationDate = publicationDate;
    }

    @PrePersist
    protected void onCreate() {
        publicationDate = new Date();
    }
}
