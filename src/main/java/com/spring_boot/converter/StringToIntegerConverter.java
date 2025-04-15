package com.spring_boot.converter;

import lombok.extern.slf4j.Slf4j;
import org.springframework.core.convert.converter.Converter;

/**
 * packageName    : com.spring_boot.converter
 * fileName       : StringToIntegerConverter
 * author         : mzc01-jungminim
 * date           : 2025. 4. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 15.        mzc01-jungminim       최초 생성
 */
@Slf4j
public class StringToIntegerConverter implements Converter<String, Integer> {
    @Override
    public Integer convert(String source) {
        log.info("convert source={}", source);
        return Integer.valueOf(source);
    }
}
