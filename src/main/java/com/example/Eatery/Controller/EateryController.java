package com.example.Eatery.Controller;

import com.example.Eatery.Dto.EateryDto;
import com.example.Eatery.Dto.EateryFormDto;
import com.example.Eatery.Dto.EateryItemDto;
import com.example.Eatery.Dto.EaterySearchDto;
import com.example.Eatery.Entity.Eatery;
import com.example.Eatery.Repository.EateryImgRepository;
import com.example.Eatery.Repository.EateryRepository;
import com.example.Eatery.Service.EateryService;
import com.example.Trip.Dto.MainItemDto;
import com.example.Trip.Dto.TripFormDto;
import com.example.Trip.Dto.TripSearchDto;
import com.example.Trip.Entity.Trip;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Controller
public class EateryController {
    private final EateryService eateryService;
    private final EateryRepository eateryRepository;
    private final EateryImgRepository eateryImgRepository;


    //맛집 전체 리스트
    @GetMapping("/eaterys")
    public String getEaterys(Model model) {
        List<EateryDto> eaterys = eateryService.findAll()
                .stream()
                .map(EateryDto::new)
                .toList();
        model.addAttribute("eaterys", eaterys);

        return "eatery/eateryPage";
    }


    //조건별로 여행지 페이징처리
    @GetMapping(value = {"/main/eaterys", "/main/eaterys/{page}"})
    public String eateryManage(EaterySearchDto eaterySearchDto,
                               @PathVariable("page") Optional<Integer> page, Model model) {
        Pageable pageable = PageRequest.of(page.isPresent() ? page.get() : 0, 10);

        Page<EateryItemDto> eaterys = eateryService.getEateryPage(eaterySearchDto, pageable);
        model.addAttribute("eaterys", eaterys);
        model.addAttribute("eaterySearchDto", eaterySearchDto);
        model.addAttribute("maxPage", 5);

        return "eatery/eateryPage";
    }

//        //조건별로 필터링
//    @GetMapping("/main/eaterys/filtered")
//    public String getFilteredEateryList(
//            @RequestParam(name = "address", required = false) String address,
//            @RequestParam(name = "eaterycategory", required = false) String eaterycategory,
//            @PageableDefault(size = 10) Pageable pageable, Model model) {
//
//        Page<Eatery> eaterys = eateryService.findByAddressAndEateryCategory(address, eaterycategory, pageable);
//        model.addAttribute("eaterys", eaterys);
//
//        return "eatery/eateryPage";
//    }

    //음식점 등록
    @GetMapping(value = "/admin/eatery/new")
    public String eateryForm(Model model) {
        model.addAttribute("eateryFormDto", new EateryFormDto());
        return "eatery/eateryForm";
    }

    @PostMapping(value = "/admin/eatery/new")
    public String eateryNew(@Valid EateryFormDto eateryFormDto, BindingResult bindingResult, Model model,
                          @RequestParam("eateryImgFile") List<MultipartFile> eateryImgFileList) {
        if (bindingResult.hasErrors()) {
            return "eatery/eateryForm";
        }
        //첫번째 이미지를 등록하지 않았을 때
        if (eateryImgFileList.get(0).isEmpty() && eateryFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력 값입니다.");
            return "eatery/eateryForm";
        }
        try {
            eateryService.saveEatery(eateryFormDto, eateryImgFileList);
        } catch (Exception e) {
            model.addAttribute("errorMessage", "여행지 등록중 오류 발생");
            return "eatery/eateryForm";
        }
        return "redirect:/";
    }


    // 여행지 상세 보기
    @GetMapping(value = "/admin/eatery/{id}")
    public String eateryDtl(@PathVariable("id") Long id, Model model) {
        try {
            EateryFormDto eateryFormDto = eateryService.getEateryDtl(id);
            model.addAttribute("eatery", eateryFormDto);

        } catch (EntityNotFoundException e) {
            model.addAttribute("errorMessage", "존재하지 않는 여행지 입니다.");
            model.addAttribute("eateryFormDto", new EateryFormDto());

            return "eatery/eatery";
        }
        return "eatery/eatery";
    }

    //음식점 수정
    @PostMapping(value = "/admin/eatery/edit/{id}")
    public String eateryUpdate(@Valid EateryFormDto eateryFormDto, BindingResult bindingResult, @RequestParam("eateryImgFile") List<MultipartFile> eateryImgFileList, Model model) {
        if (bindingResult.hasErrors()) {
            return "eatery/eateryForm";
        }
        if (eateryImgFileList.get(0).isEmpty() && eateryFormDto.getId() == null) {
            model.addAttribute("errorMessage", "첫번째 이미지는 필수 입력값입니다.");
            return "eatery/eateryForm";
        }
        try {
            eateryService.updateEatery(eateryFormDto, eateryImgFileList);

        } catch (Exception e) {
            model.addAttribute("errorMessage", "수정 중 에러가 발생");
            return "eatery/eateryForm";
        }
        return "redirect:/";
    }


    //음식점 수정
    @GetMapping("/admin/eatery/edit/{id}")
    public String showEditEateryForm(@PathVariable Long id, Model model) {
        Eatery eatery = eateryService.findById(id);
        model.addAttribute("eatery", eatery);
        return "eatery/eateryForm";
    }


    // 음식점 삭제
    @Transactional
    @RequestMapping(value = {"/admin/eatery/delete/{id}"}, method = {RequestMethod.GET, RequestMethod.POST})
    public String deleteById(@PathVariable Long id) {
        Eatery eatery = eateryRepository.findById(id)
                .orElseThrow(EntityNotFoundException::new);

        eateryImgRepository.deleteByEatery(eatery);
        eateryRepository.deleteById(id);

        return "redirect:/";
    }

//    //조건별로 필터링
//    @GetMapping("/main/eaterys/filtered")
//    public String getFilteredEateryList(
//            @RequestParam(name = "address", required = false) String address,
//            @RequestParam(name = "eaterycategory", required = false) String eateryCategory,
//            @PageableDefault(size = 10) Pageable pageable, Model model) {
//
//        Page<Eatery> eateryPage = eateryService.findByAddressAndEateryCategory(address, eateryCategory, pageable);
//        model.addAttribute("eaterys", eateryPage);
//
//        return "eatery/eateryPage"; // 이 부분은 실제 사용하는 템플릿 파일명으로 변경해야 합니다.
//    }




}