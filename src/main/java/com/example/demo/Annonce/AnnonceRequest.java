package com.example.demo.Annonce;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Setter
@Getter
public class AnnonceRequest {
    String Title;
    String Description;
    List<String> images;
}
