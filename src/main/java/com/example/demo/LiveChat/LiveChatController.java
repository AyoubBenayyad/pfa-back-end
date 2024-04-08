package com.example.demo.LiveChat;

import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class LiveChatController {
    private final UserService userService;


    @MessageMapping("/user.addUser")
    @SendTo("/user/topic")
    public User addUser(@Payload  User user){

        userService.ConnectUser(user);
            return user;
    }

    @MessageMapping("/user.disconnectUser")
    @SendTo("/user/topic")
    public User disconnect(@Payload  User user){

        userService.Disconnect(user);
        return user;
    }

    @GetMapping("/onlineUsers")
    public ResponseEntity<List<User>> findConnectedUsers(){
        return ResponseEntity.ok(userService.findConnectedUsers());
    }
    @GetMapping("/api/v1/UsersList/{userId}")
    public ResponseEntity<List<UserResponseDto>> findUsersList(@PathVariable Long userId){
        List<UserResponseDto> usersList = userService.findUsersList(userId);
        return ResponseEntity.ok(usersList);
    }


    @GetMapping("/api/v1/AddChat/{userId}")
    public ResponseEntity<List<UserResponseDto>> findAddChatUsersList(@PathVariable Long userId){
        List<UserResponseDto> usersList = userService.findAddChatUsersList(userId);
        return ResponseEntity.ok(usersList);
    }
    @GetMapping("/api/v1/messagingUserInfo/{userId}")
    public ResponseEntity<UserResponseDto> getUserInfo(@PathVariable Long userId){
        UserResponseDto user = userService.findUser(userId);

        return ResponseEntity.ok(user);
    }

}
