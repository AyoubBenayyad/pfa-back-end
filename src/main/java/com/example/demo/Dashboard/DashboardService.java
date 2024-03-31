package com.example.demo.Dashboard;

import com.example.demo.Annonce.Annonce;
import com.example.demo.Annonce.AnnonceRepo;
import com.example.demo.Annonce.Offre.Offre;
import com.example.demo.Annonce.Offre.OffreType;
import com.example.demo.Dashboard.Stats.*;
import com.example.demo.Domains.Domain;
import com.example.demo.Domains.DomainRepo;
import com.example.demo.user.User;
import com.example.demo.user.UserRepository;
import com.example.demo.user.appRoleRepo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@RequiredArgsConstructor
@Service
public class DashboardService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AnnonceRepo annonceRepo;
    @Autowired
    private appRoleRepo appRoleRepo;
    @Autowired
    private DomainRepo domainRepo;
    private final List<Domain> info = new ArrayList<>();
    private final List<Domain> gtr = new ArrayList<>();
    private final List<Domain> msa = new ArrayList<>();
    private final List<Domain> gseii = new ArrayList<>();
    private final List<Domain> gesi = new ArrayList<>();
    private final List<Domain> indus = new ArrayList<>();

    @PostConstruct
    public void init() {
        info.add(domainRepo.findByName("Informatique"));
        info.add(domainRepo.findByName("Dev Mobile"));
        info.add(domainRepo.findByName("DataScience et Analytics"));
        info.add(domainRepo.findByName("Systeme d'informations"));
        info.add(domainRepo.findByName("Devops"));
        info.add(domainRepo.findByName("Buisness Intelligence"));
        info.add(domainRepo.findByName("ERP"));
        info.add(domainRepo.findByName("Bloc chain"));

        gtr.add(domainRepo.findByName("Cyber Security"));
        gtr.add(domainRepo.findByName("NetworkSecurity"));
        gtr.add(domainRepo.findByName("Routing"));
        gtr.add(domainRepo.findByName("Secops"));

        msa.add(domainRepo.findByName("Macanique"));
        msa.add(domainRepo.findByName("Industrie"));

        indus.add(domainRepo.findByName("Macanique"));
        indus.add(domainRepo.findByName("Industrie"));


        gseii.add(domainRepo.findByName("Systemes embarques"));
        indus.add(domainRepo.findByName("Industrie"));

        gesi.add(domainRepo.findByName("Genie Energitique"));


    }

    public AdminInfosResponse getAdminInfos(Long id){
        Optional<User> user = userRepository.findById(id);
        User admin = user.get();
        AdminInfosResponse response = new AdminInfosResponse(
                admin.getFirstname()+" "+admin.getLastname(),
                admin.getEmail(),
                admin.getImageUrl());
        return response;
    }

    public StatsResponse getStats(){

       return new StatsResponse(userRepository.countUsersByRolesIs(appRoleRepo.findByRoleName("USER")),
                annonceRepo.countOffreType(OffreType.Internship)
               ,annonceRepo.countOffreType(OffreType.Job),
               userRepository.countUsersByRolesIs(appRoleRepo.findByRoleName("ADMIN")));


    }

    public List<UsersTableResponse> getUsers(int page){
        List<UsersTableResponse> usersResponse = new ArrayList<>();
        Pageable pageable = PageRequest.
                of(page , 3);
        for (User user: userRepository.findUsersByRolesIsOrderByFirstnameAsc(
                appRoleRepo.findByRoleName("USER"),pageable)
             ) {
            UsersTableResponse usersTableResponse = new UsersTableResponse(user.getId(),
                    user.getFirstname()+" "+user.getLastname(),
                    user.getEmail(),
                    user.getImageUrl(),
                    user.getEnabled()
            );
            usersResponse.add(usersTableResponse);
        }
        return usersResponse;
    }

    public void blockUser(Long id){
        Optional<User> user = userRepository.findById(id);
        User userBlocked = user.get();
        userBlocked.setEnabled(!userBlocked.getEnabled());
        userRepository.save(userBlocked);
    }
    public Long maxUsers(){

        return userRepository.countUsersByRolesIs(appRoleRepo.findByRoleName("USER"));
    }

    public OffreByCityResponse getOffreStats(){
        List<Long> statsJob = new ArrayList<>();
        List<Long> statsInt = new ArrayList<>();
        statsJob.add(annonceRepo.countOffreTypeByCity(OffreType.Job,"Casablanca"));
        statsInt.add(annonceRepo.countOffreTypeByCity(OffreType.Internship,"Casablanca"));
        statsJob.add(annonceRepo.countOffreTypeByCity(OffreType.Job,"Fes"));
        statsInt.add(annonceRepo.countOffreTypeByCity(OffreType.Internship,"Fes"));
        statsJob.add(annonceRepo.countOffreTypeByCity(OffreType.Job,"Rabat"));
        statsInt.add(annonceRepo.countOffreTypeByCity(OffreType.Internship,"Rabat"));
        statsJob.add(annonceRepo.countOffreTypeByCity(OffreType.Job,"Tanger"));
        statsInt.add(annonceRepo.countOffreTypeByCity(OffreType.Internship,"Tanger"));
        statsJob.add(annonceRepo.countOffreTypeByCity(OffreType.Job,"Meknes"));
        statsInt.add(annonceRepo.countOffreTypeByCity(OffreType.Internship,"Meknes"));
        statsJob.add(annonceRepo.countOffreTypeByCity(OffreType.Job,"Tetouan"));
        statsInt.add(annonceRepo.countOffreTypeByCity(OffreType.Internship,"Tetouan"));
        OffreByCityResponse response = new OffreByCityResponse(statsJob,statsInt);
        return response;
    }

    public OffreQuestionStatResponse getOffreQuestionStats(){
         return new OffreQuestionStatResponse(
                 annonceRepo.countOffre(),
                 annonceRepo.countQuestion()
        );
    }

    public List<Long> getStarsStats(){
        List<Long> stats = new ArrayList<>();
        stats.add(userRepository.UsersPerstars(0L,1L));
        stats.add(userRepository.UsersPerstars(1L,2L));
        stats.add(userRepository.UsersPerstars(2L,3L));
        stats.add(userRepository.UsersPerstars(3L,4L));
        stats.add(userRepository.UsersPerstars(4L,5L));
        return stats;
    }

    public DatesMonth getFirstAndLastDaysOfMonth(String year) {
        List<Date[]> firstAndLastDaysOfMonth = new ArrayList<>();

        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            Calendar calendar = Calendar.getInstance();

            // Iterate over each month in the given year
            for (int month = 0; month < 12; month++) {
                // Set the calendar to the first day of the month
                calendar.set(Calendar.YEAR, Integer.parseInt(year));
                calendar.set(Calendar.MONTH, month);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                // Extract the first day of the month
                Date firstDayOfMonth = calendar.getTime();

                // Set the calendar to the last day of the month
                int lastDayOfMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH);
                calendar.set(Calendar.DAY_OF_MONTH, lastDayOfMonth);
                // Extract the last day of the month
                Date lastDayOfMonthDate = calendar.getTime();

                // Add first and last days to the list
                Date[] firstAndLast = {firstDayOfMonth, lastDayOfMonthDate};
                firstAndLastDaysOfMonth.add(firstAndLast);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Date> datesDebut = new ArrayList<>();
        List<Date> datesEnd = new ArrayList<>();


        // Print the first and last days of each month
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        for (Date[] date : firstAndLastDaysOfMonth) {
            datesDebut.add(date[0]);
            datesEnd.add(date[1]);
        }
        DatesMonth datesMonth = new DatesMonth();
        datesMonth.beginDate = datesDebut;
        datesMonth.endDate = datesEnd;
        return datesMonth;
    }

    public AnnoncePerMonthResponse getPostsDates(){
        AnnoncePerMonthResponse response = new AnnoncePerMonthResponse();
        DatesMonth dates = getFirstAndLastDaysOfMonth("2024");
        for(int i=0;i<12;i++){
            response.getOffre().add(annonceRepo.countPosts(dates.beginDate.get(i),dates.endDate.get(i)));
            response.getQuestions().add(annonceRepo.countQuestions(dates.beginDate.get(i),dates.endDate.get(i)));
        }
        return response;
    }

    public class DatesMonth{
        List<Date> beginDate;
        List<Date> endDate;
    }

    public List<TopUsersResponse> getTopUsers(int page){
        List<TopUsersResponse> response = new ArrayList<>();
        Pageable pageable = PageRequest.
                of(page , 3);
        for(User u : userRepository.findUsersByRolesIsAndRateGreaterThanOrderByRateDesc(
                appRoleRepo.findByRoleName("USER"),
                (float) 2.5,
                pageable
                )){
            TopUsersResponse topUsersResponse = new TopUsersResponse(
                    u.getFirstname()+" "+u.getLastname(),
                    u.getEmail(),
                    u.getImageUrl(),
                    u.getRate()
            );
            response.add(topUsersResponse);
        }
        return response;
    }
    public Long maxTopUsers(){
        return userRepository.countUsersByRolesIsAndRateGreaterThan(appRoleRepo.findByRoleName("USER"), 2.5F);
    }

    public List<TopPostsResponse> TopPosts(int page){
        List<TopPostsResponse> response = new ArrayList<>();
        Pageable pageable = PageRequest.
                of(page , 3);
        for(Annonce offre : annonceRepo.topPosts(pageable)){
            TopUsersResponse topUsersResponse = new TopUsersResponse(
                    offre.getUserPosting().getFirstname()+" "+offre.getUserPosting().getLastname(),
                    offre.getUserPosting().getEmail(),
                    offre.getUserPosting().getImageUrl(),
                    0
            );
            TopPostsResponse topPostsResponse = new TopPostsResponse(
                    offre.getTitle(),
                    topUsersResponse,
                    (long) offre.getMark(),
                    offre.getTypeAnnonce() == OffreType.Job ? "Job" : "Intenrship"
            );
            response.add(topPostsResponse);
        }
        return response;
    }
    public int MaxPosts(){
        return annonceRepo.countPostsMax();
    }

    public FiliereStatsResponse filiereStats(){
    List<Long> users = new ArrayList<>();
    List<Long> posts = new ArrayList<>();

    users.add(userRepository.countUsersByFiliereIs("INFO"));
    users.add(userRepository.countUsersByFiliereIs("INDUS"));
    users.add(userRepository.countUsersByFiliereIs("GTR"));
    users.add(userRepository.countUsersByFiliereIs("GSEII"));
    users.add(userRepository.countUsersByFiliereIs("MSA"));
    users.add(userRepository.countUsersByFiliereIs("GESI"));

    posts.add(annonceRepo.countPostsFiliere(info));
    posts.add(annonceRepo.countPostsFiliere(indus));
    posts.add(annonceRepo.countPostsFiliere(gtr));
    posts.add(annonceRepo.countPostsFiliere(gseii));
    posts.add(annonceRepo.countPostsFiliere(msa));
    posts.add(annonceRepo.countPostsFiliere(gesi));

    FiliereStatsResponse response = new FiliereStatsResponse(users,posts);

    return response;
    }
}

