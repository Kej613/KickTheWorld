package com.example.Order.Dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

@Getter
@Setter
public class OrderDto {
    //예약할 숙소의 아이디와 숙박일수, 체크인 날짜, 체크아웃날짜를 전달받을 DTO
    @NotNull(message = "숙소아이디는 필수 입력 값입니다.")
    private Long id;

//    @Min(value = 1, message = "최소 숙박일은 1일 입니다.")
//    @Max(value = 10, message = "최대 숙박일수는 10일 입니다.")
    private int count;

    private Date checkInDate; // 체크인 날짜

    private Date checkOutDate; // 체크아웃 날짜

}