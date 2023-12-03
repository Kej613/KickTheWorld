package com.example.Stay.dto;


import com.example.Stay.Constant.StaySellStatus;
import com.example.Stay.Entity.Stay;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
public class StayFormDto {  //숙소 데이터 정보를 전달하는 DTO

    private Long id;   //숙소 코드

    @NotBlank(message = "숙소명은 필수 입력 값입니다.")
    private String name;

    private String category;   //숙소카테고리

    private int price;           //가격
    private int room;  // 객실수
    private String detail;          //숙소상세설명
    private String address;         //숙소위치
    private String service;         //숙소 서비스내용
    private String use_guide;       //이용안내
    private String amenity;         //편의시설
    private StaySellStatus staySellStatus;   //숙소 판매상태
    private LocalDateTime regTime;  //등록시간
    private LocalDateTime updateTime;       //수정시간

    private List<StayImgDto> stayImgDtoList = new ArrayList<>();   //이미지 리스트

    private List<Long> stayImgIds = new ArrayList<>();

    private static ModelMapper modelMapper = new ModelMapper();

    public Stay createStay(){
        return modelMapper.map(this, Stay.class);
    }

    public List<StayImgDto> getReversedStayImgDtoList() {
        List<StayImgDto> reversedList = new ArrayList<>(this.stayImgDtoList);
        Collections.reverse(reversedList);
        return reversedList;
    }

    public static StayFormDto of(Stay stay){
        return modelMapper.map(stay,StayFormDto.class);
    }

}
