package com.spring_boot.web.controller;

import com.spring_boot.web.dao.ItemRepository;
import com.spring_boot.web.domain.DeliveryCode;
import com.spring_boot.web.domain.Item;
import com.spring_boot.web.domain.ItemType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.*;

/**
 * packageName    : com.spring_boot.web.controller
 * fileName       : FormItemController
 * author         : mzc01-jungminim
 * date           : 2025. 4. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 10.        mzc01-jungminim       최초 생성
 */
@Slf4j
@Controller
@RequestMapping("/form/items")
@RequiredArgsConstructor
public class FormItemController {

    private final ItemRepository itemRepository;

    @ModelAttribute("regions")
    public Map<String, String> regions() {

        Map<String, String> regions = new LinkedHashMap<>();
        regions.put("SEOUL", "서울");
        regions.put("BUSAN", "부산");
        regions.put("JEJU", "제주");

        return regions;
    }

    @ModelAttribute("itemTypes")
    public ItemType[] itemTypes() {
        return ItemType.values();
    }

    @ModelAttribute("deliveryCodes")
    public List<DeliveryCode> deliveryCodes() {

        List<DeliveryCode> deliveryCodes = new ArrayList<>();
        deliveryCodes.add(new DeliveryCode("FAST","빠른 배송"));
        deliveryCodes.add(new DeliveryCode("NORMAL", "일반 배송"));
        deliveryCodes.add(new DeliveryCode("SLOW","느린 배송"));

        return deliveryCodes;
    }

    @GetMapping
    public String items(Model model) {

        List<Item> items = itemRepository.findAll();
        model.addAttribute("items", items);

        return "view/form/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "view/form/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute("item", new Item());

        return "view/form/addForm";
    }

    @PostMapping("/add")
    public String addItem(@ModelAttribute Item item, Model model, RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(item.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다.");
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice);
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (!errors.isEmpty()) {
            log.info("Here - errors {}" , errors);
            model.addAttribute("errors", errors);
            return "view/form/addForm";
        }
        log.info("Here - model {}" , model);

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/form/items/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "view/form/editForm";
    }

    @PostMapping("/{itemId}/edit")
    public String edit(@PathVariable Long itemId, @ModelAttribute Item item, Model model) {

        Map<String, String> errors = new HashMap<>();

        if(StringUtils.hasText(item.getItemName())) {
            errors.put("itemName", "상품 이름은 필수입니다.");
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice);
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (!errors.isEmpty()) {
            model.addAttribute("errors", errors);
            return "view/form/editForm";
        }

        itemRepository.update(itemId, item);

        return "redirect:/form/items/{itemId}";
    }

}

