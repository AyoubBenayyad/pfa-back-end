package com.example.demo.Annonce;


import com.example.demo.Comments.Comment;
import com.example.demo.Domains.Domain;
import com.example.demo.user.User;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.*;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "Type", discriminatorType = DiscriminatorType.STRING)
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
    @JsonBackReference
    User userPosting;

    @OneToMany(mappedBy = "postCommented")
    @JsonManagedReference
    List<Comment> comments = new ArrayList<>();

    @OneToMany(mappedBy = "annonce")
    @JsonManagedReference
    List<Photos> photos = new ArrayList<>();

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "annonce_domain",
            joinColumns = @JoinColumn(name = "annonce_id"),
            inverseJoinColumns = @JoinColumn(name = "domain_id")
    )
    @JsonManagedReference
    List<Domain> domains = new ArrayList<>();

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
