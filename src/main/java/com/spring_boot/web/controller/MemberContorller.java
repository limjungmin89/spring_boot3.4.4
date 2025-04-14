package com.spring_boot.web.controller;

import com.spring_boot.web.dao.MemberRepository;
import com.spring_boot.web.domain.Member;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.spring_boot.web.controller
 * fileName       : MemberContorller
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
@RequestMapping("/members")
public class MemberContorller {

    private final MemberRepository memberRepository;

    @RequestMapping("/add")
    public String add(@ModelAttribute("member") Member member) {
        return "/members/addMemberForm";
    }

    @PostMapping("/add")
    public String save(@Validated @ModelAttribute Member member, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "members/addMemberForm";
        }

        memberRepository.save(member);

        return "redirect:/";
    }

}
