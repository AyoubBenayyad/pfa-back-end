package com.example.demo.Annonce.Offre;


import com.example.demo.Annonce.Photos;
import com.example.demo.Comments.Comment;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.*;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class OffreResponse {
    Long Id;
    String Title;
    String Description;
    Date publicationDate;
    UserInfos userInfos;
    List<String> photos = new ArrayList<>();
    List<String> domains = new ArrayList<>();
     String city;
     String entreprise;
     String type;
     boolean bookmarked;
}
