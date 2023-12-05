package com.example.Eatery.Dto;


import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EateryItemDto {

    private Long id;
    private String name;   //음식점명
    private String imgUrl;   //이미지 url

    private String address;  //주소

    private String category;

    @QueryProjection
    public EateryItemDto(Long id, String name, String address, String category, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.category = category;
        this.address = address;
    }

}
