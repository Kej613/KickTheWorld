package com.example.Order.Entity;


import com.example.Order.Constant.OrderStatus;
import com.example.Member.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@Table(name="orders")
@Getter@Setter
public class Order {        // 예약정보
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;          // 예약자명

    private LocalDateTime orderDate;  //예약 날짜

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;  //예약 상태

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL, orphanRemoval = true, fetch= FetchType.LAZY)
    private List<OrderItem> orderItems = new ArrayList<>();     // 예약한 숙소리스트

    private LocalDateTime regTime;      //등록시간

    private LocalDateTime updateTime;       //수정시간

    @Column(name="check_in_date")
    private Date checkInDate;          //체크인 날짜
    @Column(name="check_out_date")
    private Date checkOutDate;             //체크아웃날짜


    //예약한 숙소 정보들을 담아둠
    public void addOrderItem(OrderItem orderItem) {
        orderItems.add(orderItem);
        orderItem.setOrder(this);
    }

//    // Helper method to remove an order item
//    public void removeOrderItem(OrderItem orderItem) {
//        this.orderItems.remove(orderItem);
//        orderItem.setOrder(null);
//    }

//    //숙소를 예약한 회원정보 세팅
//    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
//        Order order = new Order();
//        order.setMember(member);
//
//        for(OrderItem orderItem : orderItemList) {      // 찜에는 여러 개의 상품을 담을 수 있음
//            order.addOrderItem(orderItem);
//        }
//
//        order.setOrderStatus(OrderStatus.ORDER);
//        order.setOrderDate(LocalDateTime.now());
//        return order;
//    }

    //숙소를 예약한 회원정보 세팅
    public static Order createOrder(Member member, List<OrderItem> orderItemList, Date checkInDate, Date checkOutDate) {
        Order order = new Order();
        order.setMember(member);

        for (OrderItem orderItem : orderItemList) { // 찜에는 여러 개의 상품을 담을 수 있음
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());

        // 추가된 부분
        order.setCheckInDate(checkInDate);
        order.setCheckOutDate(checkOutDate);

        return order;
    }
    public int getTotalPrice() {            //총 예약금액을 설정하는 메소드
        int totalPrice = 0;
        for(OrderItem orderItem : orderItems){
            totalPrice += orderItem.getTotalPrice();
        }
        return totalPrice;
    }

    public void cancelOrder() {
        this.orderStatus = OrderStatus.CANCLE;

        for(OrderItem orderItem: orderItems) {
            orderItem.cancel();
        }
    }

}
