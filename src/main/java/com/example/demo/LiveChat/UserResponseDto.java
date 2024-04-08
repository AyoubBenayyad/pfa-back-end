package com.example.demo.LiveChat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@AllArgsConstructor
public class UserResponseDto {
    Long id;
    String userName;
    String Image;
}
