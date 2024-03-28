package com.example.demo.Dashboard.Stats;

import lombok.*;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class FiliereStatsResponse {
    private List<Long> users;
    private List<Long> posts;

}
