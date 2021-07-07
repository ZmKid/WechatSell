package com.zmkid.sell.form;

import lombok.Data;

/**
 * @author zhang man
 * @Description: 分类表单
 * @date 2021/7/7
 */
@Data
public class CategoryForm {

    private Integer categoryId;

    private String categoryName;

    private Integer categoryType;
}
