package com.example.Stay.dto;

import com.example.Stay.Entity.Stay;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class StayItemDto {

    private Long id;
    private String name;   //숙소명
    private String imgUrl;   //이미지 url
    private int price;      //숙소 가격

    private String address;  //주소

    private String category;  //숙소유형
    private int likeCount; //좋아수

    @QueryProjection
    public StayItemDto(Long id, String name, String address, String imgUrl, String category, Integer price, Integer likeCount) {
        this.id= id;
        this.name = name;
        this.imgUrl = "/stay/" + imgUrl + ".jpg";
        this.address = address;
        this.category = category;
        this.price = price;
        this.likeCount = likeCount;

    }
//    private static ModelMapper modelMapper = new ModelMapper();
    public static StayItemDto of(Stay stay) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(stay, StayItemDto.class);
    }

    // 인자가 없는 기본 생성자 추가
    public StayItemDto() {
    }
}
