package com.example.demo.Vote;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class VoteResponseDto {
    String Type;
    int mark;
}
