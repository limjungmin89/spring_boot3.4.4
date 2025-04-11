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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
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
@RequestMapping("/form/items/v2")
@RequiredArgsConstructor
public class FormItemControllerV2 {

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

        return "view/form/v2/items";
    }

    @GetMapping("/{itemId}")
    public String item(@PathVariable long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "view/form/v2/item";
    }

    @GetMapping("/add")
    public String addForm(Model model) {

        model.addAttribute("item", new Item());

        return "view/form/v2/addForm";
    }

//    @PostMapping("/add")
    public String addItemV1(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(item.getItemName())) {
//            errors.put("itemName", "상품 명은 필수입니다.");
            bindingResult.addError(new FieldError("item","itemName","상품 명은 필수입니다."));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
            bindingResult.addError(new FieldError("item","price","가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9,999 까지 허용합니다."));
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
//                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice);
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice));
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("bindingErrors {}" , bindingResult);
            // bindingErrors 는 model 에 담아줌
//            model.addAttribute("errors", errors);
            return "view/form/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/form/items/v2/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV2(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(item.getItemName())) {
//            errors.put("itemName", "상품 명은 필수입니다.");
//            bindingResult.addError(new FieldError("item","itemName","상품 명은 필수입니다."));
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 명은 필수입니다."));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
//            bindingResult.addError(new FieldError("item","price","가격은 1,000 ~ 1,000,000 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
//            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9,999 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999 까지 허용합니다."));
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
//                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice);
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice));
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("bindingErrors {}" , bindingResult);
            // bindingErrors 는 model 에 담아줌
//            model.addAttribute("errors", errors);
            return "view/form/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/form/items/v2/{itemId}";
    }

//    @PostMapping("/add")
    public String addItemV3(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(item.getItemName())) {
//            errors.put("itemName", "상품 명은 필수입니다.");
//            bindingResult.addError(new FieldError("item","itemName","상품 명은 필수입니다."));
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
//            bindingResult.addError(new FieldError("item","price","가격은 1,000 ~ 1,000,000 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
//            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9,999 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
//                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice);
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice));
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("bindingErrors {}" , bindingResult);
            return "view/form/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/form/items/v2/{itemId}";
    }

    @PostMapping("/add")
    public String addItemV4(@ModelAttribute Item item, BindingResult bindingResult, RedirectAttributes redirectAttributes) {

        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(item.getItemName())) {
//            errors.put("itemName", "상품 명은 필수입니다.");
//            bindingResult.addError(new FieldError("item","itemName","상품 명은 필수입니다."));
//            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
            bindingResult.rejectValue("itemName", "required");
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
//            bindingResult.addError(new FieldError("item","price","가격은 1,000 ~ 1,000,000 까지 허용합니다."));
//            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
            bindingResult.rejectValue("price","range", new Object[]{1000, 1000000}, null);
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
//            bindingResult.addError(new FieldError("item","quantity","수량은 최대 9,999 까지 허용합니다."));
//            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
            bindingResult.rejectValue("quantity", "max", new Object[]{9999}, null);
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
//                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice);
//                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice));
                bindingResult.reject("totalPriceMain", new Object[]{10000, resultPrice}, null);
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
            log.info("bindingErrors {}" , bindingResult);
            // bindingErrors 는 model 에 담아줌
//            model.addAttribute("errors", errors);
            return "view/form/v2/addForm";
        }

        Item savedItem = itemRepository.save(item);
        redirectAttributes.addAttribute("itemId", savedItem.getId());
        redirectAttributes.addAttribute("status", true);

        return "redirect:/form/items/v2/{itemId}";
    }

    @GetMapping("/{itemId}/edit")
    public String editForm(@PathVariable Long itemId, Model model) {

        Item item = itemRepository.findById(itemId);
        model.addAttribute("item", item);

        return "view/form/v2/editForm";
    }

//    @PostMapping("/{itemId}/edit")
    public String editV1(@PathVariable Long itemId, @ModelAttribute Item item, BindingResult bindingResult) {

        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(item.getItemName())) {
//            errors.put("itemName", "상품 명은 필수입니다.");
            bindingResult.addError(new FieldError("item", "itemName", "상품 명은 필수입니다."));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice);
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice));
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
//            model.addAttribute("errors", errors);
            return "view/form/v2/editForm";
        }

        itemRepository.update(itemId, item);

        return "redirect:/form/items/v2/{itemId}";
    }

//    @PostMapping("/{itemId}/edit")
    public String editV2(@PathVariable Long itemId, @ModelAttribute Item item, BindingResult bindingResult) {

        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(item.getItemName())) {
//            errors.put("itemName", "상품 명은 필수입니다.");
//            bindingResult.addError(new FieldError("item", "itemName", "상품 명은 필수입니다."));
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, null, null, "상품 명은 필수입니다."));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
//            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, null, null, "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
//            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, null, null, "수량은 최대 9,999 까지 허용합니다."));
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice);
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice));
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
//            model.addAttribute("errors", errors);
            return "view/form/v2/editForm";
        }

        itemRepository.update(itemId, item);

        return "redirect:/form/items/v2/{itemId}";
    }

    @PostMapping("/{itemId}/edit")
    public String editV3(@PathVariable Long itemId, @ModelAttribute Item item, BindingResult bindingResult) {

        Map<String, String> errors = new HashMap<>();

        if(!StringUtils.hasText(item.getItemName())) {
//            errors.put("itemName", "상품 명은 필수입니다.");
//            bindingResult.addError(new FieldError("item", "itemName", "상품 명은 필수입니다."));
            bindingResult.addError(new FieldError("item", "itemName", item.getItemName(), false, new String[]{"required.item.itemName"}, null, null));
        }
        if(item.getPrice() == null || item.getPrice() < 1000 || item.getPrice() > 1000000) {
//            errors.put("price", "가격은 1,000 ~ 1,000,000 까지 허용합니다.");
//            bindingResult.addError(new FieldError("item", "price", "가격은 1,000 ~ 1,000,000 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "price", item.getPrice(), false, new String[]{"range.item.price"}, new Object[]{1000, 1000000}, null));
        }
        if (item.getQuantity() == null || item.getQuantity() > 9999) {
//            errors.put("quantity", "수량은 최대 9,999 까지 허용합니다.");
//            bindingResult.addError(new FieldError("item", "quantity", "수량은 최대 9,999 까지 허용합니다."));
            bindingResult.addError(new FieldError("item", "quantity", item.getQuantity(), false, new String[]{"max.item.quantity"}, new Object[]{9999}, null));
        }
        //특정 필드가 아닌 복합 룰 검증
        if (item.getPrice() != null && item.getQuantity() != null) {
            int resultPrice = item.getPrice() * item.getQuantity();
            if (resultPrice < 10000) {
                errors.put("globalError", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice);
                bindingResult.addError(new ObjectError("item", "가격 * 수량의 합은 10,000원 이상이어야 합니다. 현재값 = " + resultPrice));
            }
        }
        //검증에 실패하면 다시 입력 폼으로
        if (bindingResult.hasErrors()) {
//            model.addAttribute("errors", errors);
            return "view/form/v2/editForm";
        }

        itemRepository.update(itemId, item);

        return "redirect:/form/items/v2/{itemId}";
    }

}

