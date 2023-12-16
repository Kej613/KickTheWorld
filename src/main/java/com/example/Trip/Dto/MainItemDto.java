package com.example.Trip.Dto;

import com.example.Trip.Entity.Trip;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter
@Setter
public class MainItemDto {
    private Long id;
    private String name;   //여행지명
    private String imgUrl;   //이미지 url

    private String address;  //주소

    private String theme; //테마

    @QueryProjection
    public MainItemDto(Long id, String name, String address, String theme, String imgUrl) {
        this.id= id;
        this.name = name;
        this.imgUrl = imgUrl;
        this.address = address;
        this.theme = theme;

    }

    public static MainItemDto of(Trip trip) {
        ModelMapper modelMapper = new ModelMapper();
        return modelMapper.map(trip, MainItemDto.class);
    }

    // 인자가 없는 기본 생성자 추가
    public MainItemDto() {
    }


}
