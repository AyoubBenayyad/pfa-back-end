package com.example.demo.Annonce;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
public class AnnonceServiceImpl implements IannonceService{

    private final String UPLOADED_FOLDER = "/images/";
    @Autowired
    UserRepository userRepository;
    @Autowired
    AnnonceRepo annonceRepo;
    @Autowired
    PhotosRepo photosRepo;

    List<Photos> tempPhoto = new ArrayList<>();
    @Override
    public void addAnnonce(AnnonceRequest annonceRequest,String username) {
        Optional<User> user = userRepository.findByEmail(username);
        User userPosting = user.orElse(null);

        Annonce annonce = new Annonce();
        annonce.setTitle(annonceRequest.getTitle());
        annonce.setDescription(annonceRequest.getDescription());

        try {
            // Assuming request.getImages() returns a list of Base64 encoded images
            List<String> base64Images = annonceRequest.getImages();


            // Generate unique filenames for each image
            List<String> uniqueFilenames = new ArrayList<>();
            for (int i = 0; i < base64Images.size(); i++) {
                uniqueFilenames.add(UUID.randomUUID().toString());
            }


            // Create directory if it doesn't exist
            File directory = new File(UPLOADED_FOLDER);
            if (!directory.exists()) {
                directory.mkdir();
            }else {
                System.out.println("already created");
            }

            // Save each image
            for (int i = 0; i < base64Images.size(); i++) {
                String base64Image = base64Images.get(i).split(",")[1];
                byte[] decodedBytes = Base64.getDecoder().decode(base64Image);
                String uniqueFilename = uniqueFilenames.get(i);
                Path path = Paths.get(UPLOADED_FOLDER + uniqueFilename + ".png");
                Files.write(path, decodedBytes);
                Photos photo = new Photos(uniqueFilename+ ".png",annonce);
                annonce.getPhotos().add(photo);
                tempPhoto.add(photo);
            }

        } catch (IOException e) {
            // Handle IOException
            e.printStackTrace();
        }



        userPosting.getAnnonces().add(annonce);
        annonce.setUserPosting(userPosting);

        userRepository.save(userPosting);
        annonceRepo.save(annonce);
        for(Photos photo : tempPhoto){
            photosRepo.save(photo);
        }

    }
}
