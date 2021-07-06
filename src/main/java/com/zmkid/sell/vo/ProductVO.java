package com.zmkid.sell.vo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * @author zhang man
 * @Description: 商品（包含类目）
 * @date 2021/6/6
 */
@Data
public class ProductVO {

    @JsonProperty("name")
    private String CategoryName;

    @JsonProperty("type")
    private int categoryType;

    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;


}
