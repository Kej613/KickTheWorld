package com.example.Eatery.Dto;

import com.example.Eatery.Entity.Eatery;
import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class EateryDto {

    private Long id;   //음식점 코드

    private String name;   //음식점명

    private String category;   //음식점 카테고리

    private String address;  //음식점 주소

    private String telephone;  //음식점 전화번호

    private String parking ; // 주차 정보

    private String guide ; // 이용안내

    private String detail;  //음식점 상세정보

    private String service;  //서비스

    private String menu;  //메뉴

    private String imgUrl;   //이미지 url


    public EateryDto(Eatery eatery) {
        this.id = eatery.getId();
        this.name = eatery.getName();
        this.detail = eatery.getDetail();
        this.category = eatery.getCategory();
        this.address = eatery.getAddress();
        this.service = eatery.getService();
        this.menu = eatery.getMenu();
        this.parking = eatery.getParking();
        this.guide = eatery.getGuide();

    }

}

