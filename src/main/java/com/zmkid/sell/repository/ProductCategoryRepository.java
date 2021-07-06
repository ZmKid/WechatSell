package com.zmkid.sell.repository;

import com.zmkid.sell.entity.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

/**
 * @author zhang man
 * @Description: 商品类目JPA
 * @date 2021/5/31
 */
@Repository
public interface ProductCategoryRepository extends JpaRepository<ProductCategory, String> {

    List<ProductCategory> findByCategoryTypeIn(Collection<Integer> categoryType);

}
