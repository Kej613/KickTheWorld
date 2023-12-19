package com.example.Stay.dto;

import com.example.Stay.Constant.StaySellStatus;
import com.example.Stay.Entity.Stay;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@NoArgsConstructor
@Getter
@Setter
public class StayDto {

    private Long id;  //숙소코드
    @NotBlank(message = "숙소명은 필수 입력값입니다.")
    private String name;
    private String detail;
    @NotBlank(message = "카테고리는 필수 입력값입니다.")
    private String category;
    @NotBlank(message = "숙소가격 입력은 필수 입력값입니다.")
    private int price;
    @NotBlank(message = "주소는 필수 입력값입니다.")
    private String address;
    private int people;    //최대 수용가능인원
    private int likeCount; //조회수
    private int room;  // 객실수
    private String service;   //서비스
    private String use_guide;   //이용안내
    private String amenity;  //편의시설
    private StaySellStatus staySellStatus;  //숙소 판매상태
    private LocalDateTime regTime;  //숙소 등록 날짜
    private String imgUrl;   //이미지 url

    public StayDto(Stay stay) {
        this.id = stay.getId();
        this.name = stay.getName();
        this.detail = stay.getDetail();
        this.category = stay.getCategory();
        this.room = stay.getRoom();
        this.price = stay.getPrice();
        this.likeCount = stay.getLikeCount();
        this.people = stay.getPeople();
        this.address = stay.getAddress();
        this.service = stay.getService();
        this.use_guide = stay.getUse_guide();
        this.amenity = stay.getAmenity();
        this.regTime = stay.getRegTime();
        this.staySellStatus = stay.getStaySellStatus();
    }

}
