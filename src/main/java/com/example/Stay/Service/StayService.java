package com.example.Stay.Service;


import com.example.Stay.Entity.Stay;
import com.example.Stay.Entity.StayImg;
import com.example.Stay.Repository.StayImgRepository;
import com.example.Stay.Repository.StayRepository;
import com.example.Stay.dto.StayImgDto;
import com.example.Stay.dto.StayItemDto;
import com.example.Stay.dto.StaySearchDto;
import com.example.Stay.dto.StayFormDto;
import com.example.Trip.Dto.MainItemDto;
import com.example.Trip.Dto.TripFormDto;
import com.example.Trip.Dto.TripImgDto;
import com.example.Trip.Dto.TripSearchDto;
import com.example.Trip.Entity.Trip;
import com.example.Trip.Entity.TripImg;
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
public class StayService {

    private final StayRepository stayRepository;
    private final StayImgService stayImgService;
    private final StayImgRepository stayImgRepository;



    //숙소 등록
    public Long saveStay(StayFormDto stayFormDto, List<MultipartFile> stayImgFileList) throws Exception {
        Stay stay = stayFormDto.createStay();
        stayRepository.save(stay);

        //이미지 등록
        for(int i=0;i<stayImgFileList.size();i++){
            StayImg stayImg = new StayImg();
            stayImg.setStay(stay);

            if(i == 0)
                stayImg.setRepimgYn("Y");
            else
                stayImg.setRepimgYn("N");

            stayImgService.saveStayImg(stayImg, stayImgFileList.get(i));
        }
        return stay.getId();
    }


    // 숙소 전체리스트
    public List<Stay> findAll() {
        return stayRepository.findAll();
    }

    public Stay findById(long id) {
        return stayRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("not found : " + id));
    }

    public Page<StayItemDto> findByCategory(String category, String address, Pageable pageable) {
        return stayRepository.findByCategory(category, address, pageable);
    }

//    public List<Stay> searchStays(String address, String category, Integer people, Integer price) {
//        return stayRepository.searchStays(address, category, people, price);
//    }

    //숙소 상세 조회
    @Transactional
    public StayFormDto getStayDtl(Long id){
        List<StayImg> stayImgList = stayImgRepository.findByStay_IdOrderByIdAsc(id);
        List<StayImgDto> stayImgDtoList = new ArrayList<>();

        for (StayImg stayImg : stayImgList) {
            StayImgDto stayImgDto = StayImgDto.of(stayImg);
            stayImgDtoList.add(stayImgDto);
        }

        Stay stay = stayRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);
        StayFormDto stayFormDto = StayFormDto.of(stay);
        stayFormDto.setStayImgDtoList(stayImgDtoList);

        return stayFormDto;
    }

//    숙소 페이지 쿼리
    @Transactional
    public Page<StayItemDto> getStayPage(StaySearchDto staySearchDto, Pageable pageable) {
        return stayRepository.getStayPage(staySearchDto, pageable);
    }

    //숙소 삭제
    public void deleteById(Long id) {
        stayRepository.deleteById(id);
    }


    public Long updateStay(StayFormDto stayFormDto, List<MultipartFile> stayImgFileList) throws Exception{
        //숙소 수정
        Stay stay = stayRepository.findById(stayFormDto.getId())
                .orElseThrow(EntityNotFoundException::new);
        stay.updateStay(stayFormDto);
        List<Long> stayImgIds = stayFormDto.getStayImgIds();

        //이미지 등록
        for(int i=0; i<stayImgFileList.size(); i++) {
            stayImgService.updateStayImg(stayImgIds.get(i), stayImgFileList.get(i));
        }
        return stay.getId();

    }


}
