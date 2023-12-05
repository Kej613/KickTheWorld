package com.example.Eatery.Repository;

import com.example.Eatery.Dto.EateryItemDto;
import com.example.Eatery.Dto.EaterySearchDto;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface EateryRepositoryCustom {
    Page<EateryItemDto> getEateryPage(EaterySearchDto eaterySearchDto, Pageable pageable);  //메인화면에서 여행지가 보일 페이지
}

