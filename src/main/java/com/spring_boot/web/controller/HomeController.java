package com.spring_boot.web.controller;

import com.spring_boot.web.SessionManager;
import com.spring_boot.web.dao.MemberRepository;
import com.spring_boot.web.domain.Member;
import com.spring_boot.web.domain.SessionConst;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttribute;

/**
 * packageName    : com.spring_boot.web.controller
 * fileName       : HomeController
 * author         : mzc01-jungminim
 * date           : 2025. 4. 13.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 13.        mzc01-jungminim       최초 생성
 */
@Slf4j
@Controller
@RequiredArgsConstructor
public class HomeController {

    private final MemberRepository memberRepository;
    private final SessionManager sessionManager;

//    @GetMapping("/")
    public String home() {
        return "home";
    }

//    @GetMapping("/")
    public String homeLogin(@CookieValue(name = "memberId", required = false) Long memberId, Model model) {

        if(memberId == null) {
            return "home";
        }

        Member loginMember =memberRepository.findById(memberId);
        if (loginMember == null) {
            return "home";
        }
        model.addAttribute("member", loginMember);

        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV2(HttpServletRequest request, Model model) {

        Member member = (Member) sessionManager.getSession(request);
        if(member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

//    @GetMapping("/")
    public String homeLoginV3(HttpServletRequest request, Model model) {

        HttpSession session = request.getSession();
        Member member = (Member)session.getAttribute(SessionConst.LOGIN_MEMBER);
        if(member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }

    @GetMapping("/")
    public String homeLoginV4(@SessionAttribute(name = SessionConst.LOGIN_MEMBER, required = false)Member member, Model model) {

        if(member == null) {
            return "home";
        }

        model.addAttribute("member", member);
        return "loginHome";
    }
}
