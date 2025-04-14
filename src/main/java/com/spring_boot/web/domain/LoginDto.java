package com.spring_boot.web.domain;

import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

/**
 * packageName    : com.spring_boot.web.domain
 * fileName       : LoginDto
 * author         : mzc01-jungminim
 * date           : 2025. 4. 13.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 13.        mzc01-jungminim       최초 생성
 */
@Data
public class LoginDto {

    @NotEmpty
    private String loginId;

    @NotEmpty
    private String password;

}
