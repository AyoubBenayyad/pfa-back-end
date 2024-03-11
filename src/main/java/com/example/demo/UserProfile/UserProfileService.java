package com.example.demo.UserProfile;

import com.example.demo.Annonce.Annonce;
import com.example.demo.Annonce.Photos;
import com.example.demo.Domains.Domain;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
public class UserProfileService {

    private final UserRepository userRepository;


    public UserProfileResponse getUser(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional == null){
            throw new BadCredentialsException("user doesnt exist");
        }
        User user= userOptional.get();



        return UserProfileResponse.builder()
                .firstname(user.getFirstname())
                .lastname(user.getLastname())
                .adress(user.getAdresse())
                .telephone(user.getTelephone())
                .niveau(user.getNiveau())
                .bio(user.getBio())
                .filiere(user.getFiliere())
                .email(user.getEmail())
                .cne(user.getCne().getCne())
                .image(user.getImageUrl())
                .followersNumber((int) user.getFollowers().stream().count())
                .build();
    }

    public byte[] getUserImage(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional == null){
            throw new BadCredentialsException("user doesnt exist");
        }
        User user= userOptional.get();

        String filePath = user.getImageUrl(); // Replace with the actual image file path



        try {
            // Read the file content to a byte array
            Path path = Paths.get(filePath);
            byte[] fileContent = Files.readAllBytes(path);

             return fileContent;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return new byte[0];
    }

    public List<FollowersResponse> getFollowers(Long id) {
        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional == null){
            throw new BadCredentialsException("user doesnt exist");
        }
        User user= userOptional.get();

        List<FollowersResponse> followersResponseList=new ArrayList<>();
        Set<User> followers = user.getFollowers();
        for (User follower : followers) {

            followersResponseList.add(FollowersResponse.builder().id(follower.getId())
                    .name(follower.getFirstname()+ " "+ follower.getLastname())
                            .image(follower.getImageUrl())
                    .build());

        }
        return followersResponseList;


    }

    public List<FollowersResponse> getFollowing(Long id) {

        Optional<User> userOptional = userRepository.findById(id);
        if(userOptional == null){
            throw new BadCredentialsException("user doesnt exist");
        }
        User user= userOptional.get();

        List<FollowersResponse> followersResponseList=new ArrayList<>();
        Set<User> followings = user.getFollowing();
        for (User follower : followings) {

            followersResponseList.add(FollowersResponse.builder().id(follower.getId())
                    .name(follower.getFirstname()+ " "+ follower.getLastname())
                    .image(follower.getImageUrl())
                    .build());

        }
        return followersResponseList;
    }

    public List<ProfilePostReponse> getProfilePosts(User user) {
        Optional<User> userOptional = userRepository.findById(user.getId());
        if(userOptional == null){
            throw new BadCredentialsException("user doesnt exist");
        }
        User ourUser= userOptional.get();

        List<ProfilePostReponse> profilePostsResponse=new ArrayList<>();

        Set<Annonce> posts= ourUser.getAnnonces();
        for (Annonce annonce : posts) {

            Set<Domain> domains= annonce.getDomains();
            List<String> domainNames=new ArrayList<>();

            for(Domain domain : domains){
                domainNames.add(domain.getName());
            }

            List<Photos> photos= annonce.getPhotos();
            List<String> PostPhotos=new ArrayList<>();

            for(Photos photo : photos){
                PostPhotos.add(photo.getImage());
            }
             profilePostsResponse.add(ProfilePostReponse.builder()
                    .postId(annonce.getId())
                    .postDomains(domainNames)
                    .postImages(PostPhotos)
                    .postDescription(annonce.getDescription())
                    .postUsername(ourUser.getFirstname()+" "+ourUser.getLastname())
                    .postDate(annonce.getPublicationDate())
                    .postTitle(annonce.getTitle())
                    .build());
        }

        return profilePostsResponse;
    }
}
