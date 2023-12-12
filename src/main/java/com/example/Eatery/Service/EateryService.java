package com.example.Eatery.Service;

import com.example.Eatery.Dto.EateryFormDto;
import com.example.Eatery.Dto.EateryImgDto;
import com.example.Eatery.Dto.EateryItemDto;
import com.example.Eatery.Dto.EaterySearchDto;
import com.example.Eatery.Entity.Eatery;
import com.example.Eatery.Entity.EateryImg;
import com.example.Eatery.Repository.EateryImgRepository;
import com.example.Eatery.Repository.EateryRepository;
import com.example.Stay.Entity.Stay;
import com.example.Trip.Dto.MainItemDto;
import com.example.Trip.Dto.TripFormDto;
import com.example.Trip.Dto.TripImgDto;
import com.example.Trip.Dto.TripSearchDto;
import com.example.Trip.Entity.Trip;
import com.example.Trip.Entity.TripImg;
import com.example.Trip.Repository.TripImgRepository;
import com.example.Trip.Service.TripImgService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional
public class EateryService {

    private final EateryRepository eateryRepository;
    private final EateryImgService eateryImgService;
    private final EateryImgRepository eateryImgRepository;


    //음식점 등록
    public Long saveEatery(EateryFormDto eateryFormDto, List<MultipartFile> eateryImgFileList) throws Exception {
        Eatery eatery = eateryFormDto.createEatery();
        eateryRepository.save(eatery);

        //이미지 등록
        for(int i=0; i<eateryImgFileList.size(); i++){
            EateryImg eateryImg = new EateryImg();
            eateryImg.setEatery(eatery);

            if(i == 0)
                eateryImg.setRepimgYn("Y");
            else
                eateryImg.setRepimgYn("N");

            eateryImgService.saveEateryImg(eateryImg, eateryImgFileList.get(i));
        }
        return eatery.getId();
    }

    //음식점 전체 리스트 출력
    public List<Eatery> findAll() {
        return eateryRepository.findAll();
    }

    //음식점 상세보기
    public Eatery findById(long id) {
        return eateryRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found: " + id));
    }

    //음식점 상세 조회
    @Transactional
    public EateryFormDto getEateryDtl(Long id){
        List<EateryImg> eateryImgList = eateryImgRepository.findByEatery_IdOrderByIdAsc(id);
        List<EateryImgDto> eateryImgDtoList = new ArrayList<>();

        for (EateryImg eateryImg : eateryImgList) {
            EateryImgDto eateryImgDto = EateryImgDto.of(eateryImg);
            eateryImgDtoList.add(eateryImgDto);
        }

        Eatery eatery = eateryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        EateryFormDto eateryFormDto = EateryFormDto.of(eatery);
        eateryFormDto.setEateryImgDtoList(eateryImgDtoList);

        return eateryFormDto;
    }


    //음식점 페이지 (페이징처리)
    @Transactional
    public Page<EateryItemDto> getEateryPage(EaterySearchDto eaterySearchDto, Pageable pageable) {
        return eateryRepository.getEateryPage(eaterySearchDto, pageable);
    }

    //음식점 수정
    public Long updateEatery(EateryFormDto eateryFormDto, List<MultipartFile> eateryImgFileList) throws  Exception {
        Eatery eatery = eateryRepository.findById(eateryFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        eatery.updateEatery(eateryFormDto);
        List<Long> eateryImgIds = eateryFormDto.getEateryImgIds();

        //이미지 등록
        for(int i=0; i<eateryImgFileList.size(); i++) {
            eateryImgService.updateEateryImg(eateryImgIds.get(i), eateryImgFileList.get(i));
        }
        return eatery.getId();
    }

    //조건별로 필터링 처리
    public Page<Eatery> findByAddressAndEateryCategory(String address, String eaterycategory, Pageable pageable) {
        return eateryRepository.findByAddressAndEateryCategory(address, eaterycategory, pageable);
    }

    //음식점 삭제
    public void deleteById(Long id) {
        eateryRepository.deleteById(id);
    }



}