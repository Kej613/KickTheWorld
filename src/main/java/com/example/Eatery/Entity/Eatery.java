package com.example.Eatery.Entity;

import com.example.Eatery.Dto.EateryFormDto;
import com.example.Trip.Entity.TripImg;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Entity
@Table(name="eatery")
@Getter
@Setter
@NoArgsConstructor
public class Eatery {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "eatery_id") // PK
    private Long id;   //음식점 코드

    @Column(nullable = false, length=100)
    private String name;   //음식점명

    @Column(name = "category")
    private String eaterycategory;   //음식점 카테고리

    @Column(name = "address")
    private String address;  //음식점 주소

    @Column
    private String telephone;  //음식점 전화번호

    @Column
    private String parking ; // 주차 정보

    @Lob
    @Column(columnDefinition =  "LONGTEXT")
    private String guide ; // 이용안내

    @Lob
    @Column(columnDefinition =  "LONGTEXT")
    private String detail;  //음식점 상세정보

    @Lob
    @Column(columnDefinition =  "LONGTEXT")
    private String service;  //서비스

    @Lob
    @Column(columnDefinition =  "LONGTEXT")
    private String menu;  //메뉴

    @OneToMany(mappedBy = "eatery", fetch = FetchType.EAGER)
    private List<EateryImg> eateryImgs;

    @Builder
    public Eatery(Long id, String eaterycategory, String name, String parking, String telephone, String service, String menu, String guide,
                  String address, String detail) {
        this.id = id;
        this.name = name;
        this.parking = parking;
        this.guide = guide;
        this.telephone = telephone;
        this.address = address;
        this.eaterycategory = eaterycategory;
        this.detail = detail;
        this.service = service;
        this.menu = menu;
    }

//음식점 수정
    public void updateEatery(EateryFormDto eateryFormDto) {
        this.name = eateryFormDto.getName();
        this.detail = eateryFormDto.getDetail();
        this.address = eateryFormDto.getAddress();
        this.telephone = eateryFormDto.getTelephone();
        this.parking = eateryFormDto.getParking();
        this.service = eateryFormDto.getService();
        this.guide = eateryFormDto.getGuide();
        this.menu = eateryFormDto.getMenu();
        this.eaterycategory = eateryFormDto.getEaterycategory();
    }



}
