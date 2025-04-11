package com.spring_boot.message;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
 * packageName    : com.spring_boot.message
 * fileName       : MessageSourceTest
 * author         : mzc01-jungminim
 * date           : 2025. 4. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 11.        mzc01-jungminim       최초 생성
 */
@SpringBootTest
public class MessageSourceTest {

    @Autowired
    MessageSource ms;

    @Test
    void heelloMessage() {
        String result = ms.getMessage("item", null, null);
        assertThat(result).isEqualTo("상품");
    }

    @Test
    void notFoundMessageCode() {
        assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
                .isInstanceOf(NoSuchMessageException.class);
    }
    @Test
    void notFoundMessageCodeDefaultMessage() {
        String result = ms.getMessage("no_code", null, "기본 메시지", null);
                assertThat(result).isEqualTo("기본 메시지");
    }

    @Test
    void argumentMessage() {
        String result = ms.getMessage("item.itemName", new Object[]{"품목"}, null);
        assertThat(result).isEqualTo("상품 품목");
    }

    @Test
    void defaultLang() {
        assertThat(ms.getMessage("item", null, null)).isEqualTo("상품");
        assertThat(ms.getMessage("item", null, Locale.KOREA)).isEqualTo("상품");
    }

    @Test
    void enLang() {
        assertThat(ms.getMessage("item", null, Locale.ENGLISH)).isEqualTo("Item");
    }
}
