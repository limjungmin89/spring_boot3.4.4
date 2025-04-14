package com.spring_boot.web.domain;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * packageName    : com.spring_boot.web.domain
 * fileName       : MemberDto
 * author         : mzc01-jungminim
 * date           : 2025. 4. 14.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 14.        mzc01-jungminim       최초 생성
 */
@Data
@AllArgsConstructor
public class MemberDto {
    private String memberId;
    private String name;

}
