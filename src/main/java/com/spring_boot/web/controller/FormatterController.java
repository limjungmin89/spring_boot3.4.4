package com.spring_boot.web.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * packageName    : com.spring_boot.web.controller
 * fileName       : FormatterController
 * author         : mzc01-jungminim
 * date           : 2025. 4. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 15.        mzc01-jungminim       최초 생성
 */
@Slf4j
@Controller
public class FormatterController {

    @ResponseBody
    @GetMapping("/formatter")
    public String formatter(@RequestParam Integer data) {
        log.info("data = {}", data);
        return "ok";
    }

}
