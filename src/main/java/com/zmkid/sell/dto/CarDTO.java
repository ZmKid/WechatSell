package com.zmkid.sell.dto;

import lombok.Data;

/**
 * @author zhang man
 * @Description: 购物车
 * @date 2021/6/9
 */
@Data
public class CarDTO {

    // 商品ID
    private String productId;

    // 数量
    private Integer productQuantity;

    public CarDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
