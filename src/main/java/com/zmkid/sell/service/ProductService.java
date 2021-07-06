package com.zmkid.sell.service;

import com.zmkid.sell.dto.CarDTO;
import com.zmkid.sell.entity.ProductInfo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/3
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    List<ProductInfo> findAll();

    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    // 加库存
    void increaseStock(List<CarDTO> carDTOList);

    // 减库存
    void decreaseStock(List<CarDTO> carDTOList);


}
