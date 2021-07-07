package com.zmkid.sell.controller;

import com.zmkid.sell.entity.ProductCategory;
import com.zmkid.sell.form.CategoryForm;
import com.zmkid.sell.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @author zhang man
 * @Description: 商品分类
 * @date 2021/7/7
 */
@Controller
@Slf4j
@RequestMapping("/seller/category")
public class SellerCategoryController {

    CategoryService categoryService;
    @GetMapping("/list")
    public ModelAndView list(Map<String, Object> map){
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("category/list", map);
    }

    @GetMapping("/index")
    public ModelAndView index(String categoryId, Map<String, Object> map){
        if(Strings.isNotBlank(categoryId)){
            ProductCategory category = categoryService.findOne(Integer.parseInt(categoryId));
            map.put("category", category);
        }
        return new ModelAndView("category/index", map);
    }

    @PostMapping("/save")
    public ModelAndView save(CategoryForm form,
                             BindingResult bindingResult,
                             Map<String, Object> map){
        if (bindingResult.hasErrors()) {
            map.put("msg", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        ProductCategory productCategory = new ProductCategory();
        try{
            if(form.getCategoryId() != null){
                productCategory = categoryService.findOne(form.getCategoryId());
            }
            BeanUtils.copyProperties(form, productCategory);
            categoryService.save(productCategory);
        }catch (Exception e){
            map.put("msg", e);
            map.put("url", "/sell/seller/category/index");
            return new ModelAndView("/common/error",map);
        }
        map.put("url", "/sell/seller/category/list");
        return new ModelAndView("/common/success", map);
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
