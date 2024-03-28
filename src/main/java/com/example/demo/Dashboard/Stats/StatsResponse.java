package com.example.demo.Dashboard.Stats;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
public class StatsResponse {
    private Long users;
    private Long intern;
    private Long job;
    private Long admin;
}
