package com.spring_boot.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * packageName    : com.spring_boot.web.domain
 * fileName       : ErrorResult
 * author         : mzc01-jungminim
 * date           : 2025. 4. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 15.        mzc01-jungminim       최초 생성
 */
@Data
@AllArgsConstructor
public class ErrorResult {

    private String code;
    private String message;

}
