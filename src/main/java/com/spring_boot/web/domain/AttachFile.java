package com.spring_boot.web.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

/**
 * packageName    : com.spring_boot.web.domain
 * fileName       : AttachFile
 * author         : mzc01-jungminim
 * date           : 2025. 4. 16.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 16.        mzc01-jungminim       최초 생성
 */
@Slf4j
@Data
public class AttachFile {

    private String uploadFileName;
    private String savedFileName;

    public AttachFile(String uploadFileName, String savedFileName) {
        this.uploadFileName = uploadFileName;
        this.savedFileName = savedFileName;
    }

}
