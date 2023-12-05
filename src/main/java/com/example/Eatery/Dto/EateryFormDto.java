package com.example.Eatery.Dto;


import com.example.Eatery.Entity.Eatery;
import com.example.Trip.Dto.TripFormDto;
import com.example.Trip.Dto.TripImgDto;
import com.example.Trip.Entity.Trip;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class EateryFormDto {

    private Long id;   //음식점 코드

    @NotBlank(message="음식점명은 필수 입력값입니다.")
    private String name;   //음식점명

    private String category;   //음식점 카테고리

    private String address;  //음식점 주소

    private String telephone;  //음식점 전화번호

    private String parking ; // 주차 정보

    private String guide ; // 이용안내

    private String detail;  //음식점 상세정보

    private String service;  //서비스

    private String menu;  //메뉴

    private List<EateryImgDto> eateryImgDtoList = new ArrayList<>();

    public List<EateryImgDto> getReversedEateryImgDtoList() {
        List<EateryImgDto> reversedList = new ArrayList<>(this.eateryImgDtoList);
        Collections.reverse(reversedList);
        return reversedList;
    }
    private List<Long> eateryImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Eatery createEatery(){
        return modelMapper.map(this, Eatery.class);
    }

    public static EateryFormDto of(Eatery eatery){
        return modelMapper.map(eatery,EateryFormDto.class);
    }




}
