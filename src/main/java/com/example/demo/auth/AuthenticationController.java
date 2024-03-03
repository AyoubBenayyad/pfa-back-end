package com.example.demo.auth;

import com.example.demo.Config.JwtService;
import com.example.demo.Exceptions.DuplicateResource;
import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.Rating.RatingServiceImpl;
import com.example.demo.user.User;
import io.jsonwebtoken.ExpiredJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;

@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class AuthenticationController {
    private final AuthenticationService authenticationService;
    private final RatingServiceImpl ratingService;
    @Autowired
    private JwtService jwtService;

    @PostMapping("/valide")
    public ResponseEntity<?> validateToken(@RequestBody authenticationRequest req){
        try{
            boolean isvalid =  jwtService.isTokenNotExpired(req.getEmail());
            return ResponseEntity.ok(!isvalid);
        }catch (Exception e){
            return ResponseEntity.ok(false);
        }
    }


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody registerRequest request) throws Exception {
        try{
            AuthenticationResponse register = authenticationService.register(request);
            return ResponseEntity.ok(register);
        }catch(InvalidInputException e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }catch(DuplicateResource e){
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }catch(Exception e){
            throw new Exception(e.getMessage());
        }

    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody authenticationRequest request){
        try {
            if(request.getEmail().isBlank() || request.getPassword().isBlank()) {
                throw new BadCredentialsException("Please Enter All Fields");
            }
            AuthenticationResponse response = authenticationService.authenticate(request);
            return ResponseEntity.ok(response);
        }catch (BadCredentialsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(e.getMessage());
        }
    }


    @GetMapping("/users")
    public List<User> gettingUsers(){
        return authenticationService.getUsers();
    }

    @GetMapping(path = "/confirm")
    public String confirm(@RequestParam("token") String token) {
        return authenticationService.confirmToken(token);
    }



}
