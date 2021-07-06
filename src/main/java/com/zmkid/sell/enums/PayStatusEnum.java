package com.zmkid.sell.enums;

import lombok.Getter;

/**
 * @author zhang man
 * @Description: 支付状态
 * @date 2021/6/6
 */
@Getter
public enum PayStatusEnum {

    WAIT(0, "等待支付"),
    SUCCESS(1, "支付成功"),
    ;

    private Integer code;

    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
