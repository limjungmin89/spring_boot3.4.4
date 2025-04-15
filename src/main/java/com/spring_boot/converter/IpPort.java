package com.spring_boot.converter;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;

/**
 * packageName    : com.spring_boot.converter
 * fileName       : IpPort
 * author         : mzc01-jungminim
 * date           : 2025. 4. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 15.        mzc01-jungminim       최초 생성
 */
@Getter
@EqualsAndHashCode
public class IpPort {

    private String ip;
    private int port;

    public IpPort(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

}
