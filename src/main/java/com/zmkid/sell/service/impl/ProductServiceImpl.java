package com.zmkid.sell.service.impl;

import com.zmkid.sell.dto.CarDTO;
import com.zmkid.sell.entity.ProductInfo;
import com.zmkid.sell.enums.ProductStatusEnum;
import com.zmkid.sell.enums.ResultEnum;
import com.zmkid.sell.exception.SellException;
import com.zmkid.sell.repository.ProductInfoRepository;
import com.zmkid.sell.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/3
 */
@Service
public class ProductServiceImpl implements ProductService {


    ProductInfoRepository productInfoRepository;

    @Autowired
    public void setProductInfoRepository(ProductInfoRepository productInfoRepository) {
        this.productInfoRepository = productInfoRepository;
    }

    @Override
    public ProductInfo findOne(String productId) {
        return productInfoRepository.findByProductId(productId);
    }

    @Override
    public List<ProductInfo> findAll() {
        return productInfoRepository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        int pageNo = 1;
        int pageSize = 100;
        Pageable page = PageRequest.of(pageNo - 1, pageSize);
        return null;
    }

    @Override
    public ProductInfo save(ProductInfo productInfo) {
        return null;
    }

    @Override
    public void increaseStock(List<CarDTO> carDTOList) {
        carDTOList.forEach(item -> {
            ProductInfo productInfo = this.productInfoRepository.findByProductId(item.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + item.getProductQuantity();
            productInfo.setProductStock(result);
            this.productInfoRepository.save(productInfo);
        });

    }

    @Override
    @Transactional
    public void decreaseStock(List<CarDTO> carDTOList) {
        for (CarDTO carDTO : carDTOList) {
            ProductInfo productInfo = this.productInfoRepository.findByProductId(carDTO.getProductId());
            if (productInfo == null) {
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            int result = productInfo.getProductStock() - carDTO.getProductQuantity();
            if (result < 0) {
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            this.productInfoRepository.save(productInfo);
        }
    }
}
