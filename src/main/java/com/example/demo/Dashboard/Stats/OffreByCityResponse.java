package com.example.demo.Dashboard.Stats;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Builder
@Setter
@Getter
@AllArgsConstructor
public class OffreByCityResponse {
    List<Long> job;
    List<Long> intern;
}
