package com.example.demo.Follow;


import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FollowService {

    private UserRepository userRepository;

    public FollowService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void followUser(Long userIdToFollow, String followerEmail) throws Exception {
        Optional<User> toFollow = userRepository.findById(userIdToFollow);
        Optional<User> willFollow = userRepository.findByEmail(followerEmail);


        if(toFollow == null || willFollow==null){
            throw new BadCredentialsException("user doesnt exist");
        }
        User userToFollow = toFollow.get();
        User follower = willFollow.get();

        if(follower.getFollowing().contains(userToFollow)){
            throw new Exception("user already followed");
        }
        if(userToFollow.getFollowers().contains(follower)){
            throw new Exception("user already followed");
        }
        follower.getFollowing().add(userToFollow);
        userToFollow.getFollowers().add(follower);
        userRepository.save(follower);
        userRepository.save(userToFollow);
    }

    public void unfollowUser(Long userIdToUnfollow, String unfollowerEmail) throws Exception {
        Optional<User> toUnfollow = userRepository.findById(userIdToUnfollow);
        Optional<User> willUnfollow = userRepository.findByEmail(unfollowerEmail);

        if(toUnfollow == null || willUnfollow==null){
            throw new BadCredentialsException("user doesnt exist");
        }

        User userToUnfollow = toUnfollow.get();
        User unfollower = willUnfollow.get();

        if(!unfollower.getFollowing().contains(userToUnfollow)){
            throw new Exception("user isnt in followings list");
        }
        if(!userToUnfollow.getFollowers().contains(unfollower)){
            throw new Exception("user isnt in followers list");
        }
        unfollower.getFollowing().remove(userToUnfollow);
        userToUnfollow.getFollowers().remove(unfollower);
        userRepository.save(unfollower);
        userRepository.save(userToUnfollow);
    }

    public void removeFollower(Long userId, String username) throws Exception {
        Optional<User> toRemoveOpt = userRepository.findById(userId);
        Optional<User> userOpt = userRepository.findByEmail(username);

        if(userOpt == null || toRemoveOpt==null){
            throw new BadCredentialsException("user doesnt exist");
        }

        User user= userOpt.get();
        User toRemove= toRemoveOpt.get();

        if(!user.getFollowers().contains(toRemove)){
            throw new Exception("user isnt in followers list");
        }

        if(!toRemove.getFollowing().contains(user)){
            throw new Exception("user isnt in following list");
        }
        user.getFollowers().remove(toRemove);
        toRemove.getFollowing().remove(user);

        userRepository.save(user);
        userRepository.save(toRemove);

    }
}
