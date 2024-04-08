package com.example.demo.LiveChat;

import com.example.demo.LiveChat.ChatMessage.ChatMessageRepository;
import com.example.demo.LiveChat.ChatMessage.ChatMessageService;
import com.example.demo.LiveChat.ChatRoom.ChatRoomRepository;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class UserService {

    private  final UserRepository userRepository;
    private final ChatMessageRepository chatMessageRepository;


    public void ConnectUser(User user){

        user.setStatus("online");
        userRepository.save(user);
    }
    public void Disconnect(User user){
        User StoredUser = userRepository.findById(user.getId()).orElse(null);
        if(StoredUser != null){
            StoredUser.setStatus("offline");
            userRepository.save(StoredUser);
        }

    }
    public List<User> findConnectedUsers(){

        return userRepository.findAllByStatus("online");
    }

    public UserResponseDto findUser(Long userId) {

        Optional<User> userOpt = userRepository.findById(userId);

        if(userOpt.isPresent()){
            User user= userOpt.get();
            UserResponseDto response= UserResponseDto.builder()
                    .userName(user.getFirstname() + " " +user.getLastname())
                    .Image(user.getImageUrl())
                    .id(user.getId())
                    .build();
            return response;
        }else{
            throw new NoSuchElementException("user Doesnt Exist ");
        }
    }

    public List<UserResponseDto> findUsersList(Long userId) {
        List<User> conversationUsers = chatMessageRepository.findConversationUsers(userId);
        List<UserResponseDto> response= new ArrayList<>();

        for ( User user: conversationUsers
             ) {
            response.add(UserResponseDto.builder()
                            .id(user.getId())
                            .Image(user.getImageUrl())
                            .userName(user.getFirstname() + " " +user.getLastname())
                    .build());

        }

        return response;

    }

    public List<UserResponseDto> findAddChatUsersList(Long userId) {
        Optional<User> user1 = userRepository.findById(userId);
        if(user1.isPresent()){
            Set<User> following = user1.get().getFollowing();
            List<UserResponseDto> response= new ArrayList<>();

            for ( User user: following
            ) {
                response.add(UserResponseDto.builder()
                        .id(user.getId())
                        .Image(user.getImageUrl())
                        .userName(user.getFirstname() + " " +user.getLastname())
                        .build());

            }

            return response;
        }

        return new ArrayList<>();
    }
}
