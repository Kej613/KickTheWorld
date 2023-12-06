//package com.example.Order.Entity;
//
//import com.example.Security.entity.Member;
//import jakarta.persistence.*;
//import lombok.Getter;
//import lombok.Setter;
//import lombok.ToString;
//
//@Table(name="cart")
//@ToString
//@Getter
//@Setter
//@Entity
//public class Cart {
//
//    @Id
//    @Column(name="cart_id")
//    @GeneratedValue(strategy = GenerationType.AUTO)
//    private Long id;
//
//    public static Cart createCart(Member member) {  //회원 한명당 1개의 찜리스트를 가지고 있음
//        Cart cart = new Cart();
//        cart.setMember(member);
//        return cart;
//    }
//
//}