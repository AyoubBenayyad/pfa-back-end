package com.example.demo.Annonce;


import com.example.demo.Config.JwtService;
import com.example.demo.auth.authenticationRequest;
import com.example.demo.user.User;
import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

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
