package com.example.Stay.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class StayItemDto {

    private Long id;
    private String name;   //숙소명
    private String imgUrl;   //이미지 url
    private int price;      //숙소 가격

    private String address;  //주소

    private String category;  //숙소유형

    @QueryProjection
    public StayItemDto(Long id, String name, String address, String imgUrl, String category, Integer price) {
        this.id= id;
        this.name = name;
        this.imgUrl = "/stay/" + imgUrl + ".jpg";
        this.address = address;
        this.category = category;
        this.price = price;

    }
}
