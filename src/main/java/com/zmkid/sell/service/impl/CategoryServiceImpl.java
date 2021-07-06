package com.zmkid.sell.service.impl;

import com.zmkid.sell.entity.ProductCategory;
import com.zmkid.sell.repository.ProductCategoryRepository;
import com.zmkid.sell.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author zhang man
 * @Description: 分类impl
 * @date 2021/6/1
 */
@Service
public class CategoryServiceImpl  implements CategoryService {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;

    @Override
    public ProductCategory findOne(Integer categoryId)  {
        return null;
    }

    @Override
    public List<ProductCategory> findAll() {
        return null;
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryType) {
        return this.productCategoryRepository.findByCategoryTypeIn(categoryType);
    }
}
