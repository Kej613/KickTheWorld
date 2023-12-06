package com.example.Order.Service;

import com.example.Order.Dto.OrderDto;
import com.example.Order.Dto.OrderHistDto;
import com.example.Order.Dto.OrderItemDto;
import com.example.Order.Entity.Order;
import com.example.Order.Entity.OrderItem;
import com.example.Order.Repository.OrderRepository;
import com.example.Member.entity.Member;
import com.example.Member.repository.MemberRepository;
import com.example.Stay.Entity.Stay;
import com.example.Stay.Entity.StayImg;
import com.example.Stay.Repository.StayImgRepository;
import com.example.Stay.Repository.StayRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.thymeleaf.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class OrderService {

    private final StayRepository stayRepository;
    private final MemberRepository memberRepository;
    private final OrderRepository orderRepository;
    private final StayImgRepository stayImgRepository;


    // 예약
    public Long order(OrderDto orderDto, String memId) {

        Stay stay = stayRepository.findById(orderDto.getId())    // 예약할 숙소를 조회
                .orElseThrow(EntityNotFoundException::new);

        Member member = memberRepository.findByMemId(memId);  //현재 로그인한 회원의 아이디를 이용해 회원 정보 조회

        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = OrderItem.createOrderItem(stay, orderDto.getCount());
        orderItemList.add(orderItem);
        Order order = Order.createOrder(member, orderItemList);    // 숙소 정보 저장

        orderRepository.save(order);

        return order.getId();
    }


    //예약한 숙소 리스트
    @Transactional
    public Page<OrderHistDto> getOrderList(String memId, Pageable pageable) {

        List<Order> orders = orderRepository.findOrders(memId, pageable);
        Long totalCount = orderRepository.countOrder(memId);

        List<OrderHistDto> orderHistDtos = new ArrayList<>();

        for (Order order : orders) {
            OrderHistDto orderHistDto = new OrderHistDto(order);
            List<OrderItem> orderItems = order.getOrderItems();
            for (OrderItem orderItem : orderItems) {
                StayImg stayImg = stayImgRepository.findByStay_IdAndRepimgYn
                        (orderItem.getStay().getId(), "Y");
                OrderItemDto orderItemDto =
                        new OrderItemDto(orderItem, stayImg.getImgUrl());
                orderHistDto.addOrderItemDto(orderItemDto);
            }

            orderHistDtos.add(orderHistDto);
        }

        return new PageImpl<OrderHistDto>(orderHistDtos, pageable, totalCount);
    }


    @Transactional
    public boolean validateOrder(Long orderId, String memId) {
        Member curMember = memberRepository.findByMemId(memId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        Member savedMember = order.getMember();

        if(!StringUtils.equals(curMember.getMemId(),savedMember.getMemId())) {
            return false;
        }
        return true;
    }



    public void cancelOrder(Long orderId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(EntityNotFoundException::new);
        order.cancelOrder();
    }




}
