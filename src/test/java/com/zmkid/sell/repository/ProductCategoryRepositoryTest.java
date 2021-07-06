package com.zmkid.sell.repository;


import com.zmkid.sell.entity.ProductCategory;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

/**
 * @author zhang man
 * @Description:  单元测试
 * @date 2021/5/31
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class ProductCategoryRepositoryTest {

    @Autowired
    private ProductCategoryRepository productCategoryRepository;


    // 该注解在单元测试，所有数据操作都会回滚；
    @Test
    public void saveTest(){
         productCategoryRepository.save(new ProductCategory("饼干",1));
    }

    @Test
    @Transactional
    public void transactionalTest(){
        productCategoryRepository.save(new ProductCategory("水",1));
    }

    @Test
    public void test(){
        int[] nums = {2,11,7,15};
        int target = 9;
        int[] nums1 = this.getNums(nums, target);


    }

    public int[] getNums(int[] nums,int target ){
        Map<Integer,Integer> map = new HashMap<>();
        for (int i = 0; i < nums.length; i++) {
            if(map.containsKey(target-nums[i])){
                return new int[]{map.get(target-nums[i]),i};
            }else {
                map.put(nums[i],i);
            }
        }
        return new int[0];
    }
}