package com.spring_boot.web.domain;

/**
 *packageName    : com.spring_boot.web.domain
 * fileName       : ItemType
 * author         : mzc01-jungminim
 * date           : 2025. 4. 10.
 * description    :
 * ===========================================================
 * DATE              AUTHOR             NOTE
 * -----------------------------------------------------------
 * 2025. 4. 10.        mzc01-jungminim       최초 생성
 */
public enum ItemType {

    BOOK("도서"), FOOD("식품"), ETC("기타");
    private final String description;
    ItemType(String description) {
        this.description = description;
    }
    public String getDescription() {
        return description;
    }
}
