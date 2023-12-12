package com.example.Trip.Entity;


import com.example.Trip.Dto.TripFormDto;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.util.List;

@EntityListeners(AuditingEntityListener.class)
@Table(name="trip")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Trip {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "trip_id")
    private Long id;  //여행지 코드

    @Column(nullable = false, length=100)
    private String name;  //여행지 이름

    @Lob
    @Column(columnDefinition =  "LONGTEXT")
    private String detail; //여행지 상세 정보

    @Column
    private String address; //여행지 주소

    @Column
    private String telephone ; //여행지 문의 및 전화

    @Column
    private String link ; //여행지 링크

    @Column
    private String parking ; // 주차 정보

    @Column
    private String service;  //서비스

    @Column
    private String theme; //여행테마

    @OneToMany(mappedBy = "trip", fetch = FetchType.EAGER)
    private List<TripImg> tripImgs;

    @Builder
    public Trip(Long id, String name, String detail, String address, String telephone, String link,
                String parking, String service, String theme) {
        this.id = id;
        this.name = name;
        this.detail = detail;
        this.address = address;
        this.telephone = telephone;
        this.parking = parking;
        this.service = service;
        this.link = link;
        this.theme = theme;

    }

    //여행지 수정
    public void updateTrip(TripFormDto tripFormDto) {
        this.name = tripFormDto.getName();
        this.detail = tripFormDto.getDetail();
        this.address = tripFormDto.getAddress();
        this.telephone = tripFormDto.getTelephone();
        this.parking = tripFormDto.getParking();
        this.service = tripFormDto.getService();
        this.link = tripFormDto.getLink();
        this.theme = tripFormDto.getTheme();
    }

}
