package com.spring_boot.web.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * packageName    : com.spring_boot.web.domain
 * fileName       : ItemSaveDto
 * author         : mzc01-jungminim
 * date           : 2025. 4. 13.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 13.        mzc01-jungminim       최초 생성
 */
@Data
public class ItemSaveDto {

    @NotBlank
    private String itemName;

    @NotNull
    @Range(min=1000, max=1000000)
    private Integer price;

    @NotNull
    @Max(value = 9999)
    private Integer quantity;

    private Boolean open;
    private List<String> regions;
    private ItemType itemType;
    private String deliveryCode;

    public ItemSaveDto() {
    }

    public ItemSaveDto(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }

}
