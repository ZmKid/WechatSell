package com.zmkid.sell.service;

import com.zmkid.sell.entity.ProductCategory;

import java.util.List;

/**
 * @author zhang man
 * @Description: 分类service
 * @date 2021/6/1
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType);
}
