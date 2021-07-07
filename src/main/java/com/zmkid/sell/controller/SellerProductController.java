package com.zmkid.sell.controller;

import cn.hutool.core.util.IdUtil;
import com.zmkid.sell.entity.ProductCategory;
import com.zmkid.sell.entity.ProductInfo;
import com.zmkid.sell.enums.ProductStatusEnum;
import com.zmkid.sell.form.ProductForm;
import com.zmkid.sell.service.CategoryService;
import com.zmkid.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;

/**
 * @author zhang man
 * @Description:
 * @date 2021/7/7
 */
@Controller
@Slf4j
@RequestMapping("/seller/product")
public class SellerProductController {

    ProductService productService;

    CategoryService categoryService;

    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page", defaultValue = "1") Integer page,
                             @RequestParam(value = "size", defaultValue = "10") Integer size,
                             Map<String, Object> map){
        PageRequest pageRequest = PageRequest.of(page-1, size);
        Page<ProductInfo> pageList = productService.findAll(pageRequest);
        map.put("productInfoPage", pageList);
        map.put("currentPage", page);
        map.put("size", size);
        return new ModelAndView("/product/list", map);
    }

    @GetMapping("/index")
    public ModelAndView index( String productId, Map<String, Object> map){
        if(Strings.isNotBlank(productId)) {
            ProductInfo productInfo = productService.findOne(productId);
            map.put("productInfo", productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categoryList = categoryService.findAll();
        map.put("categoryList", categoryList);
        return new ModelAndView("/product/index", map);
    }

    @PostMapping("/save")
    @CacheEvict(cacheNames = "product", allEntries = true, beforeInvocation = true)
    public ModelAndView saveProduct(@Valid ProductForm form,
                                    BindingResult bindingResult,
                                    Map<String, Object> map){
        if (bindingResult.hasErrors()) {
            map.put("msg", Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        ProductInfo productInfo = new ProductInfo();
        try{
            if(!Strings.isBlank(productInfo.getProductId())){
                 productInfo = productService.findOne(form.getProductId());
            }else {
                form.setProductId(IdUtil.simpleUUID());
                productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
            }
            BeanUtils.copyProperties(form, productInfo);
            productService.save(productInfo);

        }catch (Exception e){
            map.put("msg", e.getMessage());
            map.put("url", "/sell/seller/product/index");
            return new ModelAndView("common/error", map);
        }
        map.put("url", "/sell/seller/product/list");
        return new ModelAndView("/common/success", map);

    }

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }
}
