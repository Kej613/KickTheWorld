package com.example.Order.Entity;


import com.example.Stay.Entity.Stay;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter@Setter
public class OrderItem {

    @Id
    @GeneratedValue
    @Column(name="order_stay_id")
    private Long id;            //예약한 숙소 아이디

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="stay_id")
    private Stay stay;              //숙소아이디

    @ManyToOne(fetch=FetchType.LAZY)
    @JoinColumn(name="order_id")
    private Order order;            //예약아이디

    private int orderPrice;  //예약가격

    private int count;      //숙박일수
    private LocalDateTime regTime;
    private LocalDateTime updateTime;

    public static OrderItem createOrderItem(Stay stay, int count) {
        OrderItem orderItem = new OrderItem();
        orderItem.setStay(stay);            // 예약할 숙소와 숙박일수를 세팅
        orderItem.setCount(count);
        orderItem.setOrderPrice(stay.getPrice());

        stay.removeStay(count);     // 숙박일수 만큼 객실수 감소
        return orderItem;
    }

    public int getTotalPrice(){             //숙박일수와 숙박가격을 곱해 총 예약금액 계산
        return orderPrice*count;
    }

    public void cancel() {
        this.getStay().addStock(count);
    }

}
