package com.zmkid.sell.enums;

import lombok.Data;
import lombok.Getter;

/**
 * @author zhang man
 * @Description: 商品状态
 * @date 2021/6/3
 */
@Getter
public enum ProductStatusEnum implements CodeEnum {
    UP(0, "在架"),
    DOWN(1, "下架");
    private Integer code;
    private String message;

    ProductStatusEnum(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
