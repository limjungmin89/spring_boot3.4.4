package com.spring_boot.validation;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.validation.DefaultMessageCodesResolver;
import org.springframework.validation.MessageCodesResolver;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * packageName    : com.spring_boot.validation
 * fileName       : MessageCodesResolverTest
 * author         : mzc01-jungminim
 * date           : 2025. 4. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 11.        mzc01-jungminim       최초 생성
 */
@SpringBootTest
public class MessageCodesResolverTest {

    MessageCodesResolver codesResolver = new DefaultMessageCodesResolver();

    @Test
    void messageCodesResolverObject() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required","item");
        for(String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
        assertThat(messageCodes).containsExactly("required.item", "required");
    }

    @Test
    void messageCodesResolverField() {
        String[] messageCodes = codesResolver.resolveMessageCodes("required","item","itemName",String.class);
        for(String messageCode : messageCodes) {
            System.out.println("messageCode = " + messageCode);
        }
        assertThat(messageCodes).containsExactly("required.item.itemName",
                "required.itemName",
                "required.java.lang.String",
                "required");
    }

}
