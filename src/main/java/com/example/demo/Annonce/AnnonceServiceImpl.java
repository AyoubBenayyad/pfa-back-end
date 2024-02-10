package com.example.demo.Annonce;

import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AnnonceServiceImpl implements IannonceService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    AnnonceRepo annonceRepo;

    @Override
    public void addAnnonce(AnnonceRequest annonceRequest,String username) {
        Optional<User> user = userRepository.findByEmail(username);
        User userPosting = user.orElse(null);

        Annonce annonce = new Annonce();
        annonce.setTitle(annonceRequest.getTitle());
        annonce.setDescription(annonceRequest.getDescription());

        userPosting.getAnnonces().add(annonce);
        annonce.setUserPosting(userPosting);

        userRepository.save(userPosting);
        annonceRepo.save(annonce);
    }
}
