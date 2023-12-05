package com.example.Stay.Entity;//package com.example.Stay.Entity;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="stay_img")
@Getter
@Setter
public class StayImg {
    @Id
    @Column(name="stay_img_id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String imgName; //이미지 파일명
    private String oriImgName;  //원본 이미지 파일명

    @Column(name="img_url")
    private String imgUrl;  //이미지 조회 경로
    private String repimgYn;  //대표 이미지 여부

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE )
    @JoinColumn(name = "stay_id")
    private Stay stay;

    //이미지 업데이트
    public void updateStayImg(String oriImgName, String imgName, String imgUrl) {
        this.oriImgName = oriImgName;
        this.imgName = imgName;
        this.imgUrl = imgUrl;
    }
}
