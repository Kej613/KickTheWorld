package com.example.Pay.domain;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@ToString
@Table(indexes = {@Index(name = "idx_pay_ord_no", columnList = "ord_no")})
public class Pay {
    // 결제인증
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name="ord_no")
    private String ordNo;               //숙소예약번호
    private String mercntId;            //가맹점아이디
    private Long trPrice;               //숙소금액
    private String productNm;           //숙소명
    private String trDay;               //거래날짜
    private String trTime;                //거래시간
    private String signature;
    private String mercntParam1;       //체크인날짜
    private String mercntParam2;        //체크아웃날짜
    private String viewType;

    // 결제
    private String authNo;  //승인번호
    private String payResultCd;
    private String payResultMsg;
    private String trNo;        //거래번호

    // 취소
    private String cancelOrdNo;
    private String cancelResultCd;
    private String cancelResultMsg;
    private String cancelTrNo;

    private String payStatus; // O, A, P, C
}
