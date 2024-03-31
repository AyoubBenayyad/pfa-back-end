package com.example.demo.UserProfile;

import com.example.demo.Annonce.Annonce;
import com.example.demo.Annonce.AnnonceRepo;
import com.example.demo.Annonce.Offre.Offre;
import com.example.demo.Annonce.Offre.OffreType;
import com.example.demo.Annonce.Photos;
import com.example.demo.Domains.Domain;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
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

    private static String UPLOADED_FOLDER = "/images/";

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

    public List<ProfilePostReponse> getProfilePosts(Long userId) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new BadCredentialsException("user doesnt exist");
        }
        User ourUser= userOptional.get();

        List<ProfilePostReponse> profilePostsResponse=new ArrayList<>();
        List<Annonce> TestPosts=userRepository.findAllAnnoncesByUserPostingOrderByPublicationDate(ourUser.getId());

        for (Annonce annonce : TestPosts) {

            List<Domain> domains= annonce.getDomains();
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
                     .city(annonce.getCity())
                     .type(annonce.getTypeAnnonce()== OffreType.Job ? "JOB" : annonce.getTypeAnnonce()== OffreType.Internship ? "INTERNSHIP" : null)
                    .build());
        }

        return profilePostsResponse;
    }

public List<ProfilePostReponse> getOtherProfilePosts(Long userId,Long user) {
        Optional<User> userOptional = userRepository.findById(userId);
        if(userOptional.isEmpty()){
            throw new BadCredentialsException("user doesnt exist");
        }
        User ourUser= userOptional.get();

        Optional<User> userOptional2 = userRepository.findById(user);
        if(userOptional2.isEmpty()){
            throw new BadCredentialsException("user doesnt exist");
        }
        User connectedUser= userOptional2.get();

        List<ProfilePostReponse> profilePostsResponse=new ArrayList<>();
        List<Annonce> TestPosts=userRepository.findAllAnnoncesByUserPostingOrderByPublicationDate(ourUser.getId());

        for (Annonce annonce : TestPosts) {

            List<Domain> domains= annonce.getDomains();
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
                     .city(annonce.getCity())
                     .type(annonce.getTypeAnnonce()== OffreType.Job ? "JOB" : annonce.getTypeAnnonce()== OffreType.Internship ? "INTERNSHIP" : null)
                     .bookmarked(annonce.getSavingUsers().contains(connectedUser))
                    .build());
        }

        return profilePostsResponse;
    }

    public void editUser(User user,EditRequest editRequest){
        Optional<User> userOptional = userRepository.findById(user.getId());
        if(userOptional.isEmpty()){
            throw new BadCredentialsException("user doesnt exist");
        }
        User editedUser = userOptional.get();

        editedUser.setFirstname(editRequest.getFirstname());
        editedUser.setLastname(editRequest.getLastname());
        editedUser.setBio(editRequest.getBio());
        editedUser.setAdresse(editRequest.getAdress());
        editedUser.setFiliere(editRequest.getFiliere());
        editedUser.setEmail(editRequest.getEmail());
        editedUser.setTelephone(editRequest.getTelephone());
        editedUser.setNiveau(editRequest.getNiveau());

        if(!editRequest.getImgUrl().isEmpty() && !editRequest.getImgUrl().equals(null)){
            String uniqueFilename = null;

            try {
                Path filePath = Paths.get(UPLOADED_FOLDER + editedUser.getImageUrl());
                Files.deleteIfExists(filePath);

                // Decode Base64 image
                String base64Image = editRequest.getImgUrl().split(",")[1];

                byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

                // Generate unique filename
                uniqueFilename = UUID.randomUUID().toString();



                // Save the image
                Path path = Paths.get(UPLOADED_FOLDER + uniqueFilename + ".png");
                Files.write(path, decodedBytes);


            } catch (IOException e) {
                e.printStackTrace();
            }
            editedUser.setImageUrl(uniqueFilename + ".png");
        }
        userRepository.save(editedUser);
    }

    public boolean isFollwing(Long id, Long userId) {

        // there function is used to check if the logged user follows another user ( it is used in other users ProfilePage)

        Optional<User> LoggedInUserOpt = userRepository.findById(id);
        Optional<User> UserToCheckOpt = userRepository.findById(userId);

        if(LoggedInUserOpt.isEmpty() || UserToCheckOpt.isEmpty()){
            throw new NoSuchElementException("user not found");
        }

        if(LoggedInUserOpt.get().getFollowing().contains(UserToCheckOpt.get())){
            return true;
        }
        return false;
    }
}
