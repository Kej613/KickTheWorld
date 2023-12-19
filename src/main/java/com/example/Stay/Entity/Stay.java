package com.example.Stay.Entity;

import com.example.Order.Exception.OutOfStockException;
import com.example.Stay.Constant.StaySellStatus;
import com.example.Stay.dto.StayFormDto;
import com.example.Trip.Entity.TripImg;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.*;

@EntityListeners(AuditingEntityListener.class)
@Table (name="stay")
@Entity
@Getter
@Setter
@NoArgsConstructor
public class Stay {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stay_id")
    private Long id;   //숙소코드
    @Column(nullable = false, length=100)
    private String name;     //숙소명
    @Column(name="category")
    private String category;   //숙소카테고리
    @Column(name="price" ,nullable = false)
    private int price;           //가격
    @Column(nullable = false)
    private int room;  // 객실수
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String detail;          //숙소상세설명
    @Column
    private String address;         //숙소위치
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String service;         //숙소 서비스내용
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String use_guide;       //이용안내
    @Lob
    @Column(columnDefinition = "LONGTEXT")
    private String amenity;         //편의시설

    @Enumerated(EnumType.STRING)
    private StaySellStatus staySellStatus;      //숙소 예약상태

    @Column
    private int people;     //최대수용가능인원수

    @CreatedDate
    @Column(name = "reg_time", updatable = false)
    private LocalDateTime regTime;  //등록시간

    @LastModifiedDate
    @Column(name = "update_time")
    private LocalDateTime updateTime;       //수정시간



    @OneToMany(mappedBy = "stay", fetch = FetchType.EAGER)
    private List<StayImg> stayImgs;

    @Column(name = "like_count")
    private int likeCount;  // 좋아요 수

    private double grade; //평점




    public void removeStay(int room) {
        int restStay = this.room - room;
        if(restStay<0) {
            throw new OutOfStockException("객실이 모두 예약 마감되었습니다. (현재 객실 수: " + this.room + ")");
        }
        this.room = restStay;
    }

    public void addStock(int room) {
        this.room += room;
    }

    @Builder
    public Stay(
            String name,
            String category,
            int price,
            int room,
            int people,
            int likeCount,
            String detail,
            String address,
            String service,
            String use_guide,
            String amenity,
            StaySellStatus staySellStatus

    ) {
        this.name = name;
        this.category = category;
        this.price = price;
        this.room = room;
        this.people = people;
        this.detail = detail;
        this.address = address;
        this.service = service;
        this.use_guide = use_guide;
        this.amenity = amenity;
        this.likeCount = likeCount;
        this.staySellStatus = staySellStatus;
    }

    public void updateStay(StayFormDto stayFormDto) {
        this.name = stayFormDto.getName();
        this.price = stayFormDto.getPrice();
        this.room = stayFormDto.getRoom();
        this.people = stayFormDto.getPeople();
        this.detail = stayFormDto.getDetail();
        this.staySellStatus = stayFormDto.getStaySellStatus();
        this.address = stayFormDto.getAddress();
        this.service = stayFormDto.getService();
        this.amenity = stayFormDto.getAmenity();
        this.category = stayFormDto.getCategory();
        this.use_guide = stayFormDto.getUse_guide();
    }

    public void increaseLikeCount() {
        this.likeCount++;
    }


}
