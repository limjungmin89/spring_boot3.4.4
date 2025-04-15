package com.spring_boot.web.controller;

import com.spring_boot.exception.UserException;
import com.spring_boot.web.domain.ErrorResult;
import com.spring_boot.web.domain.MemberDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * packageName    : com.spring_boot.web.controller
 * fileName       : ApiExV2Controller
 * author         : mzc01-jungminim
 * date           : 2025. 4. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 15.        mzc01-jungminim       최초 생성
 */
@Slf4j
@RestController
public class ApiExV2Controller {

    @ExceptionHandler(IllegalArgumentException.class)
    public ErrorResult illegalExHandle(IllegalArgumentException e) {

        log.info("[IllegalArgument Exception] ex : {}" ,e);
        ErrorResult errorResult = new ErrorResult("BAD", e.getMessage());

        return errorResult;
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResult> userExHandle(UserException e) {
        log.info("[userExHandle] ex : {}" ,e);
        return new ResponseEntity<ErrorResult>(new ErrorResult("USER-EX", e.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(code = HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler
    public ErrorResult exHandle(Exception e) {
        log.info("[exceptionHandle] ex : {}" ,e);
        return new ErrorResult("EX", e.getMessage());
    }

    @GetMapping("/api2/members/{id}")
    public MemberDto getMember(@PathVariable("id") String id) {
        if (id.equals("ex")) {
            throw new RuntimeException("잘못된 사용자");
        }
        if (id.equals("bad")) {
            throw new IllegalArgumentException("잘못된 입력 값");
        }
        if (id.equals("user-ex")) {
            throw new UserException("사용자 오류");
        }
        return new MemberDto(id, "hello " + id);
    }


}
