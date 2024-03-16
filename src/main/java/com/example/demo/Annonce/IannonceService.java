package com.example.demo.Annonce;

import com.example.demo.Annonce.InterestingProfils.Intprofil;
import com.example.demo.Annonce.Offre.Offre;
import com.example.demo.Annonce.Offre.OffreResponse;
import com.example.demo.user.User;

import java.util.List;

public interface IannonceService {

    public void addAnnonce(AnnonceRequest annonce,String username);
    public void addQuestion(QuestionRequest request, String username);
    public List<OffreResponse> getPosts(FilterRequest request,String username);
    public List<Intprofil> getInterestingProfils(String username);
    public List<Intprofil> findUsers(String fname,String lname);
}
