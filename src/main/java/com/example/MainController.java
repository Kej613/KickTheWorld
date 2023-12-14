package com.example;

import com.example.Eatery.Dto.EateryItemDto;
import com.example.Eatery.Entity.Eatery;
import com.example.Eatery.Service.EateryService;
import com.example.Stay.Entity.Stay;
import com.example.Stay.Service.StayService;
import com.example.Stay.dto.SearchResult;
import com.example.Stay.dto.StayItemDto;
import com.example.Trip.Dto.MainItemDto;
import com.example.Trip.Entity.Trip;
import com.example.Trip.Service.TripService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RequiredArgsConstructor
@Controller
public class MainController {

    private final TripService tripService;
    private final StayService stayService;
    private final EateryService eateryService;

    @GetMapping("/admin")
    public String adminPage() {
        return "adminPage_test";
    }

    @GetMapping("/")
    public String mainPage() {
        return "mainPage_test";
    }

    @GetMapping("/mypage")
    public String myPage() {
        return "myPage";
    }

    @GetMapping("/kakao")
    public String kakaoPage() { return "kakaomap"; }

    @GetMapping("/search")
    public String search(@RequestParam(value = "address", required = false) String address,
                         @RequestParam(value = "category", required = false) String category,
                         @RequestParam(value = "theme", required = false) String theme,
                         @RequestParam(value = "eaterycategory", required = false) String eaterycategory,
                         @PageableDefault(page = 0, size = 10) Pageable pageable,
                         Model model) {
        Page<Stay> stays = stayService.findByCategory(category, address, pageable);
        Page<Trip> trips = tripService.findByCategory(theme, address, pageable);
        Page<Eatery> eaterys = eateryService.findByAddressAndEateryCategory(eaterycategory, address, pageable);

        model.addAttribute("stays", stays);
        model.addAttribute("trips", trips);
        model.addAttribute("eaterys", eaterys);

//        SearchResult result = new SearchResult(stays, trips, eaterys);
//        model.addAttribute("result", result);

        return "recommendPage";
    }

    @GetMapping("/notready")
    public String notReady() {
        return "notready";
    }

}