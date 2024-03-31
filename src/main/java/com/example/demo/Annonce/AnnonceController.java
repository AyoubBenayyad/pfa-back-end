package com.example.demo.Annonce;


import com.example.demo.Annonce.InterestingProfils.Intprofil;
import com.example.demo.Annonce.InterestingProfils.UserSearch;
import com.example.demo.Exceptions.InvalidInputException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AnnonceController {

    @Autowired
    private  AnnonceServiceImpl annonceService;

    @PostMapping("/offre")
    public ResponseEntity<?> addAnnonce(@RequestBody AnnonceRequest annonceRequest) throws Exception {
            try {
                Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
                String username = authentication.getName();
                annonceService.addAnnonce(annonceRequest,username);
            }catch (InvalidInputException e){
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
            }catch (Exception e){
                throw new Exception(e.getMessage());
            }
                    return ResponseEntity.ok("Post registred successfully");
    }

    @PostMapping("/question")
    public ResponseEntity<?> addQuestion(@RequestBody QuestionRequest request) throws Exception {
        try{
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            annonceService.addQuestion(request,username);
        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
        return ResponseEntity.ok("Successfully posted");
    }

    @PostMapping("/filter")
    public ResponseEntity<?> filterAnnonce(@RequestBody FilterRequest request) throws Exception {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            return ResponseEntity.ok(annonceService.getPosts(request,username));

        }catch (Exception e){
            throw new Exception(e.getMessage());
        }
    }

    @GetMapping("/InterestingProfils")
    public ResponseEntity<?> interestingProfils() throws Exception {
        List<Intprofil> intprofils;
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String username = authentication.getName();
            intprofils = annonceService.getInterestingProfils(username);
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

        return ResponseEntity.ok(intprofils);
    }

    @PostMapping("/search")
    public ResponseEntity<?> getUsers(@RequestBody UserSearch user){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(annonceService.findUsers(user.getFname(),user.getLname(),username));
    }

    @GetMapping("/bookmark/{id}")
    public ResponseEntity<?> bookmark(@PathVariable Long id ){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        annonceService.bookmarkPost(username,id);
        return ResponseEntity.ok("post with "+id+ "saved ");
    }

    @GetMapping("/unbookmark/{id}")
    public ResponseEntity<?> unbookmark(@PathVariable Long id ){

        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        annonceService.unbookmarkPost(username,id);
        return ResponseEntity.ok("post with "+id+ "saved ");
    }
@GetMapping("/bookmarkedPosts")
    public ResponseEntity<?> bookmarkedPosts(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return ResponseEntity.ok(annonceService.bookmarkedPost(username));
    }


}
