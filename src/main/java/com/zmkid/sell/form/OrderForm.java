package com.zmkid.sell.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;

/**
 * @author zhang man
 * @Description: 表单验证
 * @date 2021/6/15
 */
@Data
public class OrderForm {
    @NotEmpty(message = "姓名必填")
    private String name;

    @NotEmpty(message = "手机号必填")
    private String phone;

    @NotEmpty(message = "地址必填")
    private String address;

    @NotEmpty(message = "openID必填")
    private String openid;

    @NotEmpty(message = "购物车不能为空")
    private String items;

}
