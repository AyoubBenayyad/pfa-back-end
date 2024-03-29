package com.example.demo.Annonce;

import com.example.demo.Annonce.InterestingProfils.Intprofil;
import com.example.demo.Annonce.Offre.*;
import com.example.demo.Comments.Comment;
import com.example.demo.Domains.Domain;
import com.example.demo.Domains.DomainRepo;
import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@Service
@Transactional
public class AnnonceServiceImpl implements IannonceService{

    private final String UPLOADED_FOLDER = "/images/";
    @Autowired
    UserRepository userRepository;
    @Autowired
    AnnonceRepo annonceRepo;
    @Autowired
    PhotosRepo photosRepo;
    @Autowired
    DomainRepo domainRepo;

    private  OffreType convertOfferType(String type) {
       if(OffreType.Job == OffreType.valueOf(type)){
            return OffreType.Job;
       }else if (OffreType.Internship == OffreType.valueOf(type)){
           return OffreType.Internship;
       }else {
           throw new InvalidInputException("invalide Offer type");
       }
    }

    List<Photos> tempPhoto = new ArrayList<>();
    @Override
    public void addAnnonce(AnnonceRequest annonceRequest,String username) {
        Optional<User> user = userRepository.findByEmail(username);
        User userPosting = user.orElse(null);

        if(annonceRequest.getDomains().isEmpty() || annonceRequest.getTitle().isBlank()
        || annonceRequest.getDescription().isBlank() || annonceRequest.getImages().isEmpty()
        || annonceRequest.getCity().isBlank() || annonceRequest.getEntreprise().isBlank()
        || annonceRequest.getType().isBlank()
        ){
            throw new InvalidInputException("Invalid inputs.");

        }

        Offre annonce = new Offre();
        annonce.setTitle(annonceRequest.getTitle());
        annonce.setDescription(annonceRequest.getDescription());
        annonce.setCity(annonceRequest.getCity());
        annonce.setEntreprise(annonceRequest.getEntreprise());
        annonce.setTypeAnnonce(convertOfferType(annonceRequest.getType()) );

        List<Domain> domains = new ArrayList<>();
        for (String s: annonceRequest.getDomains()
             ) {
            domains.add(domainRepo.findByName(s));
        }
        for (Domain d: domains
             ) {
            d.getAnnonces().add(annonce);
            domainRepo.save(d);
            annonce.getDomains().add(d);

        }

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

    @Override
    public void addQuestion(QuestionRequest request, String username) {
        Optional<User> user = userRepository.findByEmail(username);
        User userPosting = user.orElse(null);

        if(request.getTitle().isBlank() || request.getDescription().isBlank()
        ){
            throw new InvalidInputException("Invalid inputs.");
        }

        List<Domain> domains = userRepository.findDomainsByUserId(userPosting.getId());
        Question question = new Question();
        question.setTitle(request.getTitle());
        question.setDescription(request.getDescription());
        question.setDomains(domains);
        question.setUserPosting(userPosting);

        annonceRepo.save(question);
    }

    @Override
    public List<OffreResponse> getPosts(FilterRequest request,String username) {
        Optional<User> user = userRepository.findByEmail(username);
        User userConnected = user.orElse(null);

        Pageable pageable = PageRequest.
                of(request.getPage() , 3);
        OffreType postType;
        Domain domain;
        String city;

        if(request.domain.equals("All Domains")){
            domain = null;
        }else {
            domain = domainRepo.findByName(request.getDomain());
        }
        if(request.type.equals("All")){
            postType = null;
        }else {
            postType = convertOfferType(request.type);
        }
        if(request.city.equals("All cities")){
            city = null;
        }else {
            city = request.getCity();
        }
        if (request.date.equals("Date-Earliest")) {
             pageable = PageRequest.of(request.getPage(), 3, Sort.by("publicationDate").ascending());

        } else if (request.date.equals("Date-Latest")) {
             pageable = PageRequest.of(request.getPage(), 3, Sort.by("publicationDate").descending());

        }

        List<OffreResponse> offres = new ArrayList<>();
        Page<Offre> result = annonceRepo.filteredPosts(domain, postType, city, userConnected.getId(),pageable);

        for(Offre offre : result){
                OffreResponse response = new OffreResponse();
                UserInfos userInfos = new UserInfos();
                userInfos.setId(offre.getUserPosting().getId());
                userInfos.setFullName(offre.getUserPosting().getFirstname()+
                        " "+ offre.getUserPosting().getLastname());
                userInfos.setImage(offre.getUserPosting().getImageUrl());

                response.setUserInfos(userInfos);
                response.setId(offre.getId());
                response.setTitle(offre.getTitle());
                response.setDescription(offre.getDescription());
                response.setPublicationDate(offre.getPublicationDate());
                response.setType(offre.getTypeAnnonce()==OffreType.Job ? "JOB" : "INTERNSHIP");
                response.setCity(offre.getCity());
                for(Photos photos : offre.getPhotos()){
                    response.getPhotos().add(photos.getImage());
                }
                for(Domain dm : offre.getDomains()){
                    response.getDomains().add(dm.getName());
                }
                response.setEntreprise(offre.getEntreprise());
                offres.add(response);
            }
        return offres;
    }

    @Override
    public List<Intprofil> getInterestingProfils(String username) {
        Optional<User> user = userRepository.findByEmail(username);
        User userConnected = user.orElse(null);
        List<Domain> domains = userRepository.findDomainsByUserId(userConnected.getId());
        Set<User> following = userConnected.getFollowing();
        Page<User> users;
        if(following.isEmpty()){
            users = userRepository.
                    findInterestingUsers(domains, userConnected.getId(),PageRequest.of(0, 3));

        }else {
            users = userRepository.
                    findInterestingUsersExcludingFollowing(domains, userConnected.getId(),following,PageRequest.of(0, 3));

        }
       List<User> userList = users.getContent();

        List<Intprofil> intprofils = new ArrayList<>();
        for(User u : userList){
            Intprofil intprofil = new Intprofil();
            intprofil.setId(u.getId());
            intprofil.setFullName(u.getFirstname()+" "+u.getLastname());
            intprofil.setEmail(u.getEmail());
            intprofil.setImageUrl(u.getImageUrl());
            intprofil.setRating(u.getRate());
            intprofils.add(intprofil);
        }

        return intprofils;
    }

    @Override
    public List<Intprofil> findUsers(String fname, String lname,String username) {
        Optional<User> user = userRepository.findByEmail(username);
        User userConnected = user.orElse(null);
        List<User> users = userRepository.getSearchedUsers(fname,lname,userConnected.getId());
        List<Intprofil> intprofils = new ArrayList<>();
        for (User u: users
             ) {
            Intprofil intprofil = new Intprofil(u.getId(),u.getImageUrl(),u.getFirstname()+" "+u.getLastname(),u.getEmail(),0);
            intprofils.add(intprofil);
        }
        return intprofils;
    }


}
