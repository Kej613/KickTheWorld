package com.example.Eatery.Dto;

import com.example.Eatery.Entity.EateryImg;

import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class EateryImgDto {

    private Long id;

    private String imgName;     //이미지명

    private String oriImgName;      //원본이미지

    private String imgUrl;

    private String repImgYn;        //대표이미지

    private static ModelMapper modelMapper = new ModelMapper();

    public static EateryImgDto of(EateryImg eateryImg) {
        return modelMapper.map(eateryImg,EateryImgDto.class);
    }
}
