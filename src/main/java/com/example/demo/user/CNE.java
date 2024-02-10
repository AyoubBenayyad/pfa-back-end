package com.example.demo.user;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;


@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class CNE {
    @Id
    private String cne;

    @OneToOne
    @JoinColumn(name = "user_id")
    @JsonManagedReference
    private User user;
}