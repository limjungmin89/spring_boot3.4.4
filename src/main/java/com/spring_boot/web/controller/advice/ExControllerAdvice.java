package com.spring_boot.web.controller.advice;

import com.spring_boot.exception.UserException;
import com.spring_boot.web.controller.ApiExV3Controller;
import com.spring_boot.web.domain.ErrorResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * packageName    : com.spring_boot.web.controller.advice
 * fileName       : ExControllerAdvice
 * author         : mzc01-jungminim
 * date           : 2025. 4. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 15.        mzc01-jungminim       최초 생성
 */
@Slf4j
@RestControllerAdvice(assignableTypes = {ApiExV3Controller.class})
// Target all Controllers annotated with @RestController
// @ControllerAdvice(annotations = RestController.class)
// Target all Controllers within specific packages
// @ControllerAdvice("org.example.controllers")
// Target all Controllers assignable to specific classes
// @ControllerAdvice(assignableTypes = {ControllerInterface.class, AbstractController.class})
public class ExControllerAdvice {

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

}
