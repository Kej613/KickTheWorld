package com.example.Pay.controller;

import com.example.Pay.domain.Pay;
import com.example.Pay.repository.PayRepository;
import com.example.Pay.util.DateUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * n\
 * @author firstkhw
 * @date : 2019. 2. 25.
 * 통합인증 결제 관련 샘플 호출
 *
 */
@Controller
@Slf4j
@RequiredArgsConstructor
public class PayController {

	private final PayRepository payRepo;
	/**
	 * 기본 화면 로드
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/pay", method = RequestMethod.GET)
	public String index(Model model, HttpServletRequest request) throws Exception {
		String mercntId = "M2266037"; 	//가맹점 아이디

		model.addAttribute("mercntId", mercntId);
		model.addAttribute("ordNo", DateUtil.getDateTimeMillisecond() + "M_"+request.getParameter("loginUser"));
		model.addAttribute("baseUrl", request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort());
		model.addAttribute("productNm", "테스트 상품");

		return "pay/index";
	}

	@RequestMapping(value = "/callback", method = RequestMethod.POST)
	public String callback(Model model, HttpServletRequest request) throws Exception {
		String resultCd = request.getParameter("resultCd");
		String resultMsg = request.getParameter("resultMsg");
		String mercntId = request.getParameter("mercntId");
		String ordNo = request.getParameter("ordNo");
		String authNo = request.getParameter("authNo");
		String trPrice = request.getParameter("trPrice");
		String discntPrice = request.getParameter("discntPrice");
		String payPrice = request.getParameter("payPrice");
		String trDay = request.getParameter("trDay");
		String trTime = request.getParameter("trTime");
		String mercntParam1 = request.getParameter("mercntParam1");
		String mercntParam2 = request.getParameter("mercntParam2");

		// PAY_RESERV 테이블 조회
		Pay pay = payRepo.findByOrdNo(ordNo).orElseThrow(Exception::new);
		String viewType = pay.getViewType();

		if (resultCd.equals("0")) {
			model.addAttribute("resultCd", resultCd);
			model.addAttribute("resultMsg", resultMsg);
			model.addAttribute("mercntId", mercntId);
			model.addAttribute("authNo", authNo);
			model.addAttribute("ordNo", ordNo);
			model.addAttribute("trPrice", trPrice);
			model.addAttribute("discntPrice", discntPrice);
			model.addAttribute("payPrice", payPrice);
			model.addAttribute("trDay", trDay);
			model.addAttribute("trTime", trTime);
			model.addAttribute("mercntParam1", mercntParam1);
			model.addAttribute("mercntParam2", mercntParam2);
			model.addAttribute("viewType", viewType);

			pay.setAuthNo(authNo);
			pay.setPayStatus("A");
			payRepo.save(pay);
		} else {
			model.addAttribute("resultCd", resultCd);
			model.addAttribute("ordNo", ordNo);
			model.addAttribute("viewType", viewType);
		}

		return "pay/callback";
	}

	@RequestMapping(value = "/cancel", method = RequestMethod.POST)
	public String cancel(Model model, HttpServletRequest request) throws Exception {
		return "pay/cancel";
	}

//	@RequestMapping(value = "/list", method = RequestMethod.GET)
//	public String list(Model model) throws Exception {
//		model.addAttribute("payList", payRepo.findAllByPayStatusIn(List.of("P", "C")).orElse(null));
//		return "pay/list";
//	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public String list(Model model, @AuthenticationPrincipal UserDetails userDetails) throws Exception {
		String memId = userDetails.getUsername();
		model.addAttribute("payList", payRepo.findPayByMemId(memId));
		return "pay/list";
	}

}
