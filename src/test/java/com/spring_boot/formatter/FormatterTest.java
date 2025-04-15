package com.spring_boot.formatter;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.ParseException;
import java.util.Locale;

/**
 * packageName    : com.spring_boot.formatter
 * fileName       : FormatterTest
 * author         : mzc01-jungminim
 * date           : 2025. 4. 15.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 15.        mzc01-jungminim       최초 생성
 */
@SpringBootTest
public class FormatterTest {

    BasicFormatter formatter = new BasicFormatter();

    @Test
    void parse() throws ParseException {
        Number result = formatter.parse("1000", Locale.KOREA);
        Assertions.assertThat(result).isEqualTo(1000L);
    }

    @Test
    void print() {
        String result = formatter.print(1000L, Locale.KOREA);
        Assertions.assertThat(result).isEqualTo("1,000");
    }
}
