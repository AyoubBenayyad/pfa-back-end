package com.example.demo.Dashboard.Stats;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Builder
@AllArgsConstructor
@Setter
@Getter
public class OffreQuestionStatResponse {
    private Long offre;
    private Long question;
}
