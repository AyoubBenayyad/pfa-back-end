package com.example.demo.UserProfile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class ProfilePostReponse {
    private Long postId;
    private List<String> postImages;
    private List<String> postDomains;
    private Date postDate;
    private String postUsername;
    private String postTitle;
    private String postDescription;
    private String city;
    private String type;
    private boolean bookmarked;

}
