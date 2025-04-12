package com.spring_boot.web.domain;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.websocket.OnMessage;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import java.util.List;

/**
 * packageName    : com.spring_boot.web.domain
 * fileName       : Item
 * author         : mzc01-jungminim
 * date           : 2025. 4. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 10.        mzc01-jungminim       최초 생성
 */
@Data
public class Item {

    @NotNull(groups = ItemUpdate.class)
    private Long id;

    @NotBlank(groups = {ItemSave.class, ItemUpdate.class})
    private String itemName;

    @NotNull(groups = {ItemSave.class, ItemUpdate.class})
    @Range(min=1000, max=1000000, message = "{0} ~ {1} 범위를 허용합니다.")
    private Integer price;

    @NotNull(groups = {ItemSave.class, ItemUpdate.class})
    @Max(value = 9999, groups = ItemSave.class)
    private Integer quantity;

    private Boolean open;
    private List<String> regions;
    private ItemType itemType;
    private String deliveryCode;

    public Item() {
    }

    public Item(String itemName, Integer price, Integer quantity) {
        this.itemName = itemName;
        this.price = price;
        this.quantity = quantity;
    }
}
