package com.example.demo.Annonce;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class FilterRequest {
    String domain;
    String city;
    String type;
    String date;
    int page;
}
