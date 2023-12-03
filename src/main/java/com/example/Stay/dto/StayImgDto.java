package com.example.Stay.dto;


import com.example.Stay.Entity.StayImg;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

@Getter@Setter
public class StayImgDto { //이미지에 대한 데이터를 전달할 DTO

    private Long id;
    private String imgName;
    private String oriImgName;
    private String imgUrl;
    private String repImgYn;

    private static ModelMapper modelMapper = new ModelMapper();

    public static StayImgDto of(StayImg stayImg) {
        return modelMapper.map(stayImg, StayImgDto.class);
    }
}
