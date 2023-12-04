package com.example.Order.Entity;


import com.example.Order.Constant.OrderStatus;
import com.example.Security.entity.Member;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="orders")
@Getter@Setter
public class Order {
    @Id
    @GeneratedValue
    @Column(name="order_id")
    private Long id;

    @ManyToOne(fetch= FetchType.LAZY)
    @JoinColumn(name="member_id")
    private Member member;

    private LocalDateTime orderDate;  //예약 날짜

    @Enumerated(EnumType.STRING)
    private OrderStatus orderStatus;  //예약 상태

    @OneToMany(mappedBy = "order" , cascade = CascadeType.ALL, orphanRemoval = true, fetch= FetchType.LAZY) // 영속성 전이, 고아객체 제거
    private List<OrderItem> orderItems = new ArrayList<>();

    private LocalDateTime regTime;

    private LocalDateTime updateTime;


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

    //숙소를 예약한 회원정보 세팅
    public static Order createOrder(Member member, List<OrderItem> orderItemList) {
        Order order = new Order();
        order.setMember(member);

        for(OrderItem orderItem : orderItemList) {      // 찜에는 여러 개의 상품을 담을 수 있음
            order.addOrderItem(orderItem);
        }

        order.setOrderStatus(OrderStatus.ORDER);
        order.setOrderDate(LocalDateTime.now());
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