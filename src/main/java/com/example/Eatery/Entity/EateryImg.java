package com.example.Eatery.Entity;


import com.example.Trip.Entity.Trip;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="eatery_img")
@Getter
@Setter
public class EateryImg {

    @Id
    @Column(name="eatery_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String imgName; //이미지 파일명
    private String oriImgName; //원본 이미지 파일명
    private String imgUrl; //이미지 조회 경로
    private String repimgYn; //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY,  cascade = CascadeType.REMOVE  )
    @JoinColumn(name = "eatery_id" )
    private Eatery eatery;

    //    이미지 정보를 업데이트
    public void updateEateryImg(String oriImgName, String imgName, String imgUrl){
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }

}
