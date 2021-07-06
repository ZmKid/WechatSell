package com.zmkid.sell.repository;

import cn.hutool.core.util.IdUtil;
import com.zmkid.sell.entity.ProductInfo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/3
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductInfoRepositoryTest {

    @Autowired
    private ProductInfoRepository productInfoRepository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId(IdUtil.simpleUUID());
        productInfo.setProductPrice(new BigDecimal("3.5"));
        productInfo.setProductName("皮蛋瘦肉粥");
        productInfo.setProductStock(100);
        productInfo.setProductDescription("好喝");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        productInfoRepository.save(productInfo);
    }
    @Test
    public void test2(){}
}