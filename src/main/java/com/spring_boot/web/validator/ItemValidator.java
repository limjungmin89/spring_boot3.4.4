package com.spring_boot.web.validator;

import com.spring_boot.web.domain.Item;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 * packageName    : com.spring_boot.web.validator
 * fileName       : ItemValidator
 * author         : mzc01-jungminim
 * date           : 2025. 4. 11.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 11.        mzc01-jungminim       최초 생성
 */
@Component
public class ItemValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Item.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Item item = (Item) target;

        if(!StringUtils.hasText(item.getItemName())) {
            errors.rejectValue("itemName", "required");
        }

        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.rejectValue("price","range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.reject("totalPriceMain", new Object[]{10000, resultPrice}, null);
            }
        }

    }

    @Override
    public Errors validateObject(Object target) {
        return Validator.super.validateObject(target);
    }
}
