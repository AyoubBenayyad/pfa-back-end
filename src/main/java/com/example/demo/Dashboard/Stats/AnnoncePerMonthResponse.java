package com.example.demo.Dashboard.Stats;


import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class AnnoncePerMonthResponse {
    private List<Long> offre = new ArrayList<>();
    private List<Long> questions = new ArrayList<>();
}
