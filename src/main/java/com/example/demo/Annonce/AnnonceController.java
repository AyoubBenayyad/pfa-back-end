package com.example.demo.Annonce;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;

@RestController
@RequestMapping("/api/v1")
public class AnnonceController {

    @Autowired
    AnnonceServiceImpl annonceService;

    @PostMapping("/annonce")
    public ResponseEntity<?> addAnnonce(@RequestBody AnnonceRequest annonceRequest){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        annonceService.addAnnonce(annonceRequest,username);

        return ResponseEntity.ok("Post registred successfully");
    }
}
