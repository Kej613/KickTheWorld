//package com.example.Order.Dto;
//
//import com.example.Order.Entity.OrderItem;
//import lombok.Getter;
//import lombok.Setter;
//
//@Getter
//@Setter
//public class OrderItemDto {
//
//    public OrderItemDto(OrderItem orderItem , String imgUrl){
//        this.name  = orderItem.getStay().getName();
//        this.count = orderItem.getCount();
//        this.orderPrice = orderItem.getOrderPrice();
//        this.imgUrl = imgUrl;
//    }
//
//    private String name; //숙소명
//    private int count; // 객실수
//    private int orderPrice; //결제 금액
//    private String imgUrl; //숙소 이미지 경로
//
//}
package com.example.Order.Dto;

import com.example.Order.Entity.OrderItem;

import lombok.Getter;
import lombok.Setter;


import java.util.Date;

@Getter
@Setter
public class OrderItemDto {

    public OrderItemDto(OrderItem orderItem, String imgUrl) {
        this.name = orderItem.getStay().getName();
        this.count = orderItem.getCount();
        this.orderPrice = orderItem.getOrderPrice();
        this.imgUrl = imgUrl;
        // 수정된 부분
        this.checkInDate = orderItem.getCheckInDate();
        this.checkOutDate = orderItem.getCheckOutDate();
    }

    private String name; // 숙소명
    private int count; // 객실수
    private int orderPrice; // 결제 금액
    private String imgUrl; // 숙소 이미지 경로

    // 추가된 부분

    private Date checkInDate;

    private Date checkOutDate;
}
