package com.spring_boot.basic;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * packageName    : com.spring_boot.basic
 * fileName       : BasicController
 * author         : mzc01-jungminim
 * date           : 2025. 4. 9.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 9.        mzc01-jungminim       최초 생성
 */
@Controller
@RequestMapping("/basic")
public class BasicController {

    @GetMapping("/text-basic")
    public String textBasic(Model model) {

        model.addAttribute("data", "Hello <b>Spring!</b>");
        return "basic/text-basic";
    }

    @GetMapping("/text-unescaped")
    public String textUnescaped(Model model) {

        model.addAttribute("data", "Hello <b>Spring!</b>");
        return "basic/text-unescaped";
    }

}
