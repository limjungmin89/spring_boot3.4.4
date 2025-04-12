package com.spring_boot.web.controller;

import com.spring_boot.web.domain.ItemSaveDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.spring_boot.web.controller
 * fileName       : JsonItemController
 * author         : mzc01-jungminim
 * date           : 2025. 4. 13.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 13.        mzc01-jungminim       최초 생성
 */
@Slf4j
@RestController
@RequestMapping("/validation/api/items")
public class JsonItemController {

    @PostMapping("/add")
    public Object addItem(@RequestBody @Validated ItemSaveDto form, BindingResult bindingResult) {
        log.info("Start API JSON Validation");

        if (bindingResult.hasErrors()) {
            log.info("errors={}", bindingResult);
            return bindingResult.getAllErrors();
        }

        log.info("End API JSON Validation");

        return form;

    }
}
