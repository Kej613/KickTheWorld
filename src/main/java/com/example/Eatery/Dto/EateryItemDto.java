package com.example.Eatery.Dto;


import com.example.Eatery.Entity.Eatery;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class EateryItemDto {

    private Long id;
    private String name;   //음식점명
    private String imgUrl;   //이미지 url

    private String address;  //주소

    private String eaterycategory;  //음식점카테고리

    @QueryProjection
    public EateryItemDto(Long id, String name, String address, String eaterycategory, String imgUrl) {
        this.id = id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.eaterycategory = eaterycategory;
        this.address = address;
    }
    public static EateryItemDto of(Eatery eatery) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(eatery, EateryItemDto.class);
    }
    public EateryItemDto() {}

}
