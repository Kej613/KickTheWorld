package com.example.Stay.Controller;

import com.example.Eatery.Entity.Eatery;
import com.example.Eatery.Repository.EateryRepository;
import com.example.Eatery.Service.EateryService;
import com.example.Stay.Entity.Stay;
import com.example.Stay.Repository.StayImgRepository;
import com.example.Stay.Repository.StayRepository;
import com.example.Stay.Service.StayService;
import com.example.Stay.dto.*;
import com.example.Trip.Entity.Trip;
import com.example.Trip.Service.TripService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class StayController {

    private final StayService stayService;
    private final StayRepository stayRepository;
    private final StayImgRepository stayImgRepository;


    //숙소 전체 리스트
    @GetMapping("/stays")
    public String getStays(Model model) {
        List<StayDto> stays = stayService.findAll()
                .stream()
                .map(StayDto::new)
                .toList();

        model.addAttribute("stays", stays);

        return "stay/stayPage";
    }

//    //카테고리별로 숙소 조회
//    @GetMapping("/stays/category")
//    public String getStaysByCategory(@RequestParam(value = "category", required = false) String category,
//                                     @RequestParam(value = "address", required = false) String address,
//                                     @PageableDefault(page = 0, size = 10) Pageable pageable,
//                                     Model model) {
//        Page<Stay> staysByCategory = stayService.findByCategory(category, address, pageable);
//        model.addAttribute("stays", staysByCategory);
//        return "recommendPage";
//    }



    //조건별로 숙소 페이징처리
    @GetMapping(value = {"/main/stays", "/main/stays/{page}"})
    public String stayManage(StaySearchDto staySearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 6);

        Page<StayItemDto> stays = stayService.getStayPage(staySearchDto, pageable);
        model.addAttribute("stays", stays);
        model.addAttribute("staySearchDto", staySearchDto);
        model.addAttribute("maxPage", 5);
        return "stay/stayPage";
    }


    //숙소 등록
    @GetMapping("/admin/stay/new")
    public String StayForm(Model model) {
        model.addAttribute("stayFormDto", new StayFormDto());
        return "stay/stayForm";
    }

    @PostMapping("/admin/stay/new")
    public String StayNew(@Valid StayFormDto stayFormDto, BindingResult bindingResult, Model model,
                             @RequestParam("stayImgFile") List<MultipartFile> stayImgFileList) {
        if(bindingResult.hasErrors()) {
            return "stay/stayForm";
        }

        //첫번째 이미지를 등록하지 않았을 때
        if(stayImgFileList.get(0).isEmpty() && stayFormDto.getId() ==null) {
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값입니다.");
            return "stay/stayForm";
        }
        try {
            stayService.saveStay(stayFormDto, stayImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "여행지 등록중 에러 발생") ;
            return "stay/stayForm";
        }
        return "redirect:/";
    }

    //숙소 상세보기
    @GetMapping(value="/admin/stay/{id}")
    public String stayDtl(@PathVariable("id") Long id, Model model) {
        try{
            StayFormDto stayFormDto = stayService.getStayDtl(id);
            model.addAttribute("stay" , stayFormDto);

        }catch(EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 여행지 입니다.");
            model.addAttribute("stayFormDto", new StayFormDto());

            return "stay/stay";
        }
        return "stay/stay";
    }

    // 숙소수정
    @PostMapping(value = "/admin/stay/edit/{id}")
    public String stayUpdate(@Valid StayFormDto stayFormDto, BindingResult bindingResult, @RequestParam("stayImgFile") List<MultipartFile> stayImgFileList, Model model) {
        if (bindingResult.hasErrors()) {
            return "stay/stayForm";
        }
        if (stayImgFileList.get(0).isEmpty() && stayFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력값입니다.");
            return "stay/stayForm";
        }
        try {
            stayService.updateStay(stayFormDto, stayImgFileList);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "수정 중 에러가 발생");
            return "stay/stayForm";
        }
        return "redirect:/";
    }


    //숙소 수정
    @GetMapping("/admin/stay/edit/{id}")
    public String showEditStayForm(@PathVariable Long id, Model model) {
        Stay stay = stayService.findById(id);
        model.addAttribute("stay", stay);
        return "stay/stayForm";
    }


    //숙소 삭제
    @Transactional
    @RequestMapping(value = {"/admin/stay/delete/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteById(@PathVariable Long id) {
        Stay stay = stayRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        stayImgRepository.deleteByStay(stay);
        stayRepository.deleteById(id);

        return "redirect:/";
    }

//    //관리자-숙소관리
//    @GetMapping(value={"/admin/stays", "/admin/stays/{page}"}) // 숙소관리화면 페이지 진입시 페이지 번호가 있는경우와 없는경우
//    public String stayManage(StaySearchDto staySearchDto, @PathVariable("page") Optional<Integer> page, Model model) {
//        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0,10);
//        Page<Stay> stays =
//                stayService.getAdminStayPage(staySearchDto, pageable);
//                    model.addAttribute("stays", stays);
//                    model.addAttribute("staySearchDto", staySearchDto);
//                    model.addAttribute("maxPage", 5);
//                    return "stay/stayMng";
//
//    }
    @GetMapping(value = "/filter", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public ResponseEntity<List<Stay>> filterStays(@RequestParam String category) {
        List<Stay> filteredStays = stayService.findStaysByCategory(category);
        return ResponseEntity.ok(filteredStays);
    }
}

