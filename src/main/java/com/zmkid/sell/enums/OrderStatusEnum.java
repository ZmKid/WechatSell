package com.zmkid.sell.enums;

import lombok.Getter;

/**
 * @author zhang man
 * @Description: 订单状态
 * @date 2021/6/6
 */
@Getter
public enum OrderStatusEnum {

    NEW(0, "新订单"),
    FINISHED(1, "完结"),
    CANCEL(2, "取消"),
    ;

    private Integer code;

    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
