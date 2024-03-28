package com.example.demo.Dashboard;


import com.example.demo.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/admin")
@CrossOrigin(origins = "http://localhost:3000")
@RequiredArgsConstructor
public class DashboardController {
    @Autowired private DashboardService dashboardService;

    @GetMapping()
    public ResponseEntity<?> getAdminInfos(@AuthenticationPrincipal User user){
        try {
            return ResponseEntity.ok(dashboardService.getAdminInfos(user.getId()));
        }catch (Exception e){
            return ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/stats")
    public ResponseEntity<?> getStats(){
        try {
            return ResponseEntity.ok(dashboardService.getStats());
        }catch (Exception e){
            return  ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/users/{page}")
    public ResponseEntity<?> getUsers(@PathVariable int page){
        try {
            return ResponseEntity.ok(dashboardService.getUsers(page));
        }catch (Exception e){
            return  ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/block/{id}")
    public ResponseEntity<?> blockUser(@PathVariable Long id){
        try {
            dashboardService.blockUser(id);
            return ResponseEntity.ok("blocked");
        }catch (Exception e){
            return  ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/maxUsers")
    public ResponseEntity<?> getCountUsers(){
        try {
            return ResponseEntity.ok(dashboardService.maxUsers());
        }catch (Exception e){
            return  ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/offreByCity")
    public ResponseEntity<?> getOffreStats(){
        try {
            return ResponseEntity.ok(dashboardService.getOffreStats());
        }catch (Exception e){
            return  ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/offreQuestion")
    public ResponseEntity<?> getOffreQuestionStats(){
        try {
            return ResponseEntity.ok(dashboardService.getOffreQuestionStats());
        }catch (Exception e){
            return  ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/StarsStats")
    public ResponseEntity<?> getStarsStats(){
        try {
            return ResponseEntity.ok(dashboardService.getStarsStats());
        }catch (Exception e){
            return  ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/MonthlyPosts")
    public ResponseEntity<?> getPostsDates() throws Exception {
        try {
            return ResponseEntity.ok(dashboardService.getPostsDates());
        }catch (Exception e){
            throw new Exception(e);
        }
    }
    @GetMapping("/Topusers/{page}")
    public ResponseEntity<?> getTopUsers(@PathVariable int page) throws Exception {
        try {
            return ResponseEntity.ok(dashboardService.getTopUsers(page));
        }catch (Exception e){
            throw new Exception(e);
        }
    }
    @GetMapping("/maxTopUsers")
    public ResponseEntity<?> getCountTopUsers(){
        try {
            return ResponseEntity.ok(dashboardService.maxTopUsers());
        }catch (Exception e){
            return  ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/TopPosts/{page}")
    public ResponseEntity<?> getTopPosts(@PathVariable int page){
        try {
            return ResponseEntity.ok(dashboardService.TopPosts(page));
        }catch (Exception e){
            return  ResponseEntity.ok(e.getMessage());
        }
    }
    @GetMapping("/MaxPosts")
    public ResponseEntity<?> getMaxPosts(){
        try {
            return ResponseEntity.ok(dashboardService.MaxPosts());
        }catch (Exception e){
            return  ResponseEntity.ok(e.getMessage());
        }
    }

    @GetMapping("/FiliereStats")
    public ResponseEntity<?> getFiliereStats(){
        try {
            return ResponseEntity.ok(dashboardService.filiereStats());
        }catch (Exception e){
            return  ResponseEntity.ok(e.getMessage());
        }
    }
}
