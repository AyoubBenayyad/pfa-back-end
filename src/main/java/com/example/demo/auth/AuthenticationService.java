package com.example.demo.auth;

import com.example.demo.Config.JwtService;
import com.example.demo.Domains.Domain;
import com.example.demo.Domains.DomainRepo;
import com.example.demo.Exceptions.DuplicateResource;
import com.example.demo.Exceptions.InvalidInputException;
import com.example.demo.auth.email.EmailService;
import com.example.demo.auth.emailToken.ConfirmationToken;
import com.example.demo.auth.emailToken.ConfirmationTokenService;
import com.example.demo.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final appRoleRepo appRoleRepo;
    private final CneRepo cneRepo;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;
    private static String UPLOADED_FOLDER = "/images/";
    public void addNewRole(appRole role){
        appRoleRepo.save(role);
    }
    @Autowired
    DomainRepo domainRepo;

    public void addRoleToUser(String email,String role){
        Optional<User> currentUser = userRepository.findByEmail(email);
        appRole RoleName = appRoleRepo.findByRoleName(role);
        currentUser.get().getRoles().add(RoleName);
    }

    public AuthenticationResponse register(registerRequest request) {

        if(
                request.getPassword().isBlank() || request.getFirstname().isBlank()
                || request.getLastname().isBlank() || !emailValidation(request.getEmail())
                || request.getBiographie().isBlank() || request.getFiliere().isBlank()
                || request.getNiveau().isBlank() || request.getCne().isBlank()
                || request.getImage().isBlank() || request.getDomains().size() < 3
        )
        {
            throw new InvalidInputException("Invalid Inputs");
        }
        else {
            if (userRepository.existsUserByEmail(request.getEmail())) {
                throw new DuplicateResource("User with email " + request.getEmail() + " already exits");
            }
            if (!cneRepo.existsByCne(request.getCne())) {
                throw new InvalidInputException("No student found with cne: " + request.getCne());
            } else if (userRepository.existsUserByCne(cneRepo.findByCne(request.getCne()))) {
                throw new DuplicateResource("User with cne " + request.getCne() + " already exits");
            }


            String uniqueFilename = null;

            try {
                // Decode Base64 image
                String base64Image = request.getImage().split(",")[1];

                byte[] decodedBytes = Base64.getDecoder().decode(base64Image);

                // Generate unique filename
                uniqueFilename = UUID.randomUUID().toString();

                // Create directory if it doesn't exist
                File directory = new File(UPLOADED_FOLDER);
                if (!directory.exists()) {
                    directory.mkdir();
                }else{
                    System.out.println("folder exists");
                }

                // Save the image
                Path path = Paths.get(UPLOADED_FOLDER + uniqueFilename + ".png");
                Files.write(path, decodedBytes);


            } catch (IOException e) {
                e.printStackTrace();
            }
            Collection<appRole> rolesOfUser = new ArrayList<>();
            appRole RoleName = appRoleRepo.findByRoleName("USER");
            Collection<appRole> list = new ArrayList<>();
            list.add(RoleName);
        //domains list:
            Set<Domain> domainsList = new HashSet<>();
            for (String s: request.getDomains()
            ) {
                domainsList.add(domainRepo.findByName(s));
            }

            var CNE=cneRepo.findByCne(request.getCne());
            var user = User.builder()
                    .firstname(request.getFirstname())
                    .Lastname(request.getLastname())
                    .email(request.getEmail())
                    .password(passwordEncoder.encode(request.getPassword()))
                    .telephone(request.getTelephone())
                    .cne(CNE)
                    .bio(request.getBiographie())
                    .adresse(request.getAdresse())
                    .filiere(request.getFiliere())
                    .niveau(request.getNiveau())
                    .imageUrl(uniqueFilename + ".png")
                    .enabled(false)
                    .locked(false)
                    .roles(list)
                    .domains(domainsList)
                    .build();



            userRepository.save(user);
            for (Domain d: domainsList
            ) {
                d.getDomainUsers().add(user);
                domainRepo.save(d);
            }

            CNE.setUser(user);
            cneRepo.save(CNE);


            String token = UUID.randomUUID().toString();
            ConfirmationToken confirmationToken = new ConfirmationToken(
                    token,
                    LocalDateTime.now(),
                    LocalDateTime.now().plusMinutes(15),
                    user
            );
            confirmationTokenService.saveConfirmationToken(confirmationToken);
            String link = "http://localhost:8080/api/v1/auth/confirm?token=" + token;
            emailService.send(request.getEmail(), buildEmail(request.getLastname(), link));
            var jwtToken = jwtService.generateToken(user);

            return AuthenticationResponse.builder()
                    .token(jwtToken).build();
        }

    }

    public List<User> getUsers(){
        return userRepository.findAll();
    }

    public AuthenticationResponse authenticate(authenticationRequest request) {
        try{
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(),request.getPassword())
            );
            var user = userRepository.findByEmail(request.getEmail()).orElseThrow(() -> new AuthenticationException("Invalid email/password supplied") {
            });
            var jwtToken=jwtService.generateToken(user);
            return AuthenticationResponse.builder()
                    .token(jwtToken).build();
        }
        catch (AuthenticationException e){
            throw new BadCredentialsException(e.getMessage());
        }

    }

    public static boolean emailValidation(String email) {
        String emailRegex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,4}$";
        Pattern pattern = Pattern.compile(emailRegex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.find();
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken = confirmationTokenService
                .getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("token not found"));

        if (confirmationToken.getConfirmedAt() != null) {
            throw new IllegalStateException("email already confirmed");
        }

        LocalDateTime expiredAt = confirmationToken.getExpiresAt();

        if (expiredAt.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("token expired");
        }

        confirmationTokenService.setConfirmedAt(token);
       userRepository.enableAppUser(
                confirmationToken.getUser().getEmail());
        return "confirmed";
    }


    private String buildEmail(String name, String link) {
        return "<div style=\"font-family:Helvetica,Arial,sans-serif;font-size:16px;margin:0;color:#0b0c0c\">\n" +
                "\n" +
                "<span style=\"display:none;font-size:1px;color:#fff;max-height:0\"></span>\n" +
                "\n" +
                "  <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;min-width:100%;width:100%!important\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"100%\" height=\"53\" bgcolor=\"#0b0c0c\">\n" +
                "        \n" +
                "        <table role=\"presentation\" width=\"100%\" style=\"border-collapse:collapse;max-width:580px\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" align=\"center\">\n" +
                "          <tbody><tr>\n" +
                "            <td width=\"70\" bgcolor=\"#0b0c0c\" valign=\"middle\">\n" +
                "                <table role=\"presentation\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td style=\"padding-left:10px\">\n" +
                "                  \n" +
                "                    </td>\n" +
                "                    <td style=\"font-size:28px;line-height:1.315789474;Margin-top:4px;padding-left:10px\">\n" +
                "                      <span style=\"font-family:Helvetica,Arial,sans-serif;font-weight:700;color:#ffffff;text-decoration:none;vertical-align:top;display:inline-block\">Confirm your email</span>\n" +
                "                    </td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "              </a>\n" +
                "            </td>\n" +
                "          </tr>\n" +
                "        </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td width=\"10\" height=\"10\" valign=\"middle\"></td>\n" +
                "      <td>\n" +
                "        \n" +
                "                <table role=\"presentation\" width=\"100%\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse\">\n" +
                "                  <tbody><tr>\n" +
                "                    <td bgcolor=\"#1D70B8\" width=\"100%\" height=\"10\"></td>\n" +
                "                  </tr>\n" +
                "                </tbody></table>\n" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\" height=\"10\"></td>\n" +
                "    </tr>\n" +
                "  </tbody></table>\n" +
                "\n" +
                "\n" +
                "\n" +
                "  <table role=\"presentation\" class=\"m_-6186904992287805515content\" align=\"center\" cellpadding=\"0\" cellspacing=\"0\" border=\"0\" style=\"border-collapse:collapse;max-width:580px;width:100%!important\" width=\"100%\">\n" +
                "    <tbody><tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "      <td style=\"font-family:Helvetica,Arial,sans-serif;font-size:19px;line-height:1.315789474;max-width:560px\">\n" +
                "        \n" +
                "            <p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\">Hi " + name + ",</p><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> Thank you for registering. Please click on the below link to activate your account: </p><blockquote style=\"Margin:0 0 20px 0;border-left:10px solid #b1b4b6;padding:15px 0 0.1px 15px;font-size:19px;line-height:25px\"><p style=\"Margin:0 0 20px 0;font-size:19px;line-height:25px;color:#0b0c0c\"> <a href=\"" + link + "\">Activate Now</a> </p></blockquote>\n Link will expire in 15 minutes. <p>See you soon</p>" +
                "        \n" +
                "      </td>\n" +
                "      <td width=\"10\" valign=\"middle\"><br></td>\n" +
                "    </tr>\n" +
                "    <tr>\n" +
                "      <td height=\"30\"><br></td>\n" +
                "    </tr>\n" +
                "  </tbody></table><div class=\"yj6qo\"></div><div class=\"adL\">\n" +
                "\n" +
                "</div></div>";
    }
}
