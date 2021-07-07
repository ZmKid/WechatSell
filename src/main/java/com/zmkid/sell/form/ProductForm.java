package com.zmkid.sell.form;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author zhang man
 * @Description: 修改商品信息表单
 * @date 2021/7/7
 */
@Data
public class ProductForm {
    private String productId;

    /** 名字. */
    private String productName;

    /** 单价. */
    private BigDecimal productPrice;

    /** 库存. */
    private Integer productStock;

    /** 描述. */
    private String productDescription;

    /** 小图. */
    private String productIcon;

    /** 类目编号. */
    private Integer categoryType;
}
