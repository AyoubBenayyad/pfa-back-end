package com.example.demo.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;


@Data
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
    private String password;

    @OneToOne(mappedBy = "user")
    private CNE cne;

    private String bio;
    private String niveau;
    private String filiere;
    private String imageUrl;
    private Boolean locked=false;
    private Boolean enabled=false;

    @ManyToMany(fetch = FetchType.EAGER)
    private Collection<appRole> roles = new ArrayList<>();

    public User(long id, String firstname, String lastname, String mail, String password) {
        this.id=id;
        this.firstname=firstname;
        this.Lastname=lastname;
        this.password=password;
        this.email=mail;
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
