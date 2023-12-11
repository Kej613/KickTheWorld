package com.example.Eatery.Repository;

import com.example.Eatery.Dto.EateryItemDto;
import com.example.Eatery.Dto.EaterySearchDto;
import com.example.Eatery.Dto.QEateryItemDto;
import com.example.Eatery.Entity.QEatery;
import com.example.Eatery.Entity.QEateryImg;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.thymeleaf.util.StringUtils;

import java.util.List;

public class EateryRepositoryCustomImpl implements  EateryRepositoryCustom {

    private JPAQueryFactory queryFactory;

    public EateryRepositoryCustomImpl(EntityManager em){
        this.queryFactory = new JPAQueryFactory(em);
    }

    //음식점명, 지역 검색 쿼리
    private BooleanExpression searchByLike(String searchBy, String searchQuery) {
        if(StringUtils.equals("name", searchBy)) {
            return QEatery.eatery.name.like("%" + searchQuery + "%");

        }else if(StringUtils.equals("address", searchBy)) {
            return QEatery.eatery.address.like("%" + searchQuery+ "%");

        }else if(StringUtils.equals("eaterycategory", searchBy)) {
            return QEatery.eatery.eaterycategory.like("%" + searchQuery+"%");
        }
        return null;
    }

    private BooleanExpression nameLike(String searchQuery){
        return StringUtils.isEmpty(searchQuery) ? null : QEatery.eatery.name.like("%" + searchQuery + "%");
    }


    @Override
    public Page<EateryItemDto> getEateryPage (EaterySearchDto eaterySearchDto, Pageable pageable) {
        QEatery eatery = QEatery.eatery;
        QEateryImg eateryImg = QEateryImg.eateryImg;

        QueryResults<EateryItemDto> results = queryFactory
                .select(
                        new QEateryItemDto(
                                eatery.id,
                                eatery.name,
                                eatery.address,
                                eatery.eaterycategory,
                                eateryImg.imgUrl)
                )
                .from(eateryImg)
                .join(eateryImg.eatery, eatery)
                .where(eateryImg.repimgYn.eq("Y"))
                .where(searchByLike(eaterySearchDto.getSearchBy(),eaterySearchDto.getSearchQuery()))
                .orderBy(eatery.id.desc())
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();

        List<EateryItemDto> content = results.getResults();
        long total = results.getTotal();
        return new PageImpl<>(content,pageable, total);
    }



}
