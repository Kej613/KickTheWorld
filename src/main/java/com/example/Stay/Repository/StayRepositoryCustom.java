package com.example.Stay.Repository;

import com.example.Stay.Entity.Stay;
import com.example.Stay.dto.StayItemDto;
import com.example.Stay.dto.StaySearchDto;
import com.example.Trip.Dto.MainItemDto;
import com.example.Trip.Dto.TripSearchDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface StayRepositoryCustom {
//    Page<Stay> getAdminStayPage(StaySearchDto staySearchDto, Pageable pageable);
    Page<StayItemDto> getStayPage(StaySearchDto staySearchDto, Pageable pageable);  //숙소 페이지

}
