package com.example.demo.user;

import com.example.demo.Annonce.Annonce;
import com.example.demo.Annonce.Offre.Offre;
import com.example.demo.Comments.Comment;
import com.example.demo.Domains.Domain;
import com.example.demo.Rating.UserRating;
import com.example.demo.Vote.Vote;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_user")
public class User implements UserDetails {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String firstname;
    private String Lastname;
    @Column(
            nullable = false,
            unique=true
    )
    private String email;
    @JsonIgnore
    private String password;

    private String Status;
    @OneToOne(mappedBy = "user")
    @JsonBackReference
    private CNE cne;
    private String bio;
    private String niveau;
    private String filiere;
    private String imageUrl;
    private String telephone;
    private String adresse;
    private Boolean locked=false;
    private Boolean enabled=false;
    private int nmbOfVotes = 0;
    private float rate = 0;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<appRole> roles = new ArrayList<>();

//follower and following----------------------------------------------------
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(
            name = "user_followers",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "follower_id")
    )
    @JsonManagedReference
    private Set<User> followers = new HashSet<>();


    @ManyToMany(mappedBy = "followers",fetch = FetchType.LAZY)
    @JsonBackReference
    private Set<User> following = new HashSet<>();
//----------------------------------------------------------------------------

//Rated and Rating-----------------------------------------------------------

    @OneToMany(mappedBy = "ratingUser",fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<UserRating> RatingUsers = new HashSet<>();


    @OneToMany(mappedBy = "ratedUser",fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<UserRating> ratedUsers = new HashSet<>();
//-----------------------------------------------------------------------------

//one user can post multiple posts
    @OneToMany(mappedBy = "userPosting",fetch = FetchType.LAZY)
    @JsonManagedReference
    private Set<Annonce> annonces = new HashSet<>();

//One user can comment multible comments to multiple posts
    @OneToMany(mappedBy = "userCommenting")
    @JsonManagedReference
    private Set<Comment> comments = new HashSet<>();
//liste of domains:
@ManyToMany(fetch = FetchType.LAZY)
@JoinTable(
        name = "user_domain",
        joinColumns = @JoinColumn(name = "user_id"),
        inverseJoinColumns = @JoinColumn(name = "domain_id")
)
@JsonManagedReference
    private Set<Domain> domains = new HashSet<>();

    @OneToMany(mappedBy = "user")
    @JsonManagedReference
    private Set<Vote> votes;

    @ManyToMany(mappedBy = "savingUsers",fetch = FetchType.LAZY)
    @JsonBackReference
    private List<Annonce> bookmarked = new ArrayList<>();

    public User(long id, String firstname, String lastname, String mail, String password) {
        this.id=id;
        this.firstname=firstname;
        this.Lastname=lastname;
        this.password=password;
        this.email=mail;
    }

    //setter
    public void setRate(float rate,float oldRate,boolean updateTest) {


        if(!updateTest){
            if(this.nmbOfVotes == 0){
                this.rate = rate;
            }else {
                this.rate= ((this.rate * this.nmbOfVotes)+ rate) / (this.nmbOfVotes+1);
            }
            this.nmbOfVotes++;

        }else{
            if(this.nmbOfVotes == 0){
                this.rate = rate;
            }else {
                this.rate= ((this.rate * this.nmbOfVotes) - oldRate + rate) / (this.nmbOfVotes);
            }
        }


    }

    public float getRate() {
        return rate;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> authorities = new ArrayList<>();
        roles.forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        });
        return authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }
}
