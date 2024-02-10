package com.example.demo.Follow;


import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowService {

    private UserRepository userRepository;

    public FollowService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void followUser(Long userIdToFollow, String followerEmail) {
        Optional<User> toFollow = userRepository.findById(userIdToFollow);
        Optional<User> willFollow = userRepository.findByEmail(followerEmail);

        User userToFollow = toFollow.orElse(null);
        User follower = willFollow.orElse(null);

        follower.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(follower);
        userRepository.save(follower);
        userRepository.save(userToFollow);
    }

    public void unfollowUser(Long userIdToUnfollow, String unfollowerEmail) {
        Optional<User> toUnfollow = userRepository.findById(userIdToUnfollow);
        Optional<User> willUnfollow = userRepository.findByEmail(unfollowerEmail);

        User userToUnfollow = toUnfollow.orElse(null);
        User unfollower = willUnfollow.orElse(null);

        unfollower.getFollowing().remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(unfollower);
        userRepository.save(unfollower);
        userRepository.save(userToUnfollow);
    }
}
