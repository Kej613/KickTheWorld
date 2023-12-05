package com.example.Eatery.Dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EaterySearchDto {

    private String searchBy;  //지역과 음식점명을 검색
    private String searchQuery = "";
}
