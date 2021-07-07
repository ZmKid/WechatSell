package com.zmkid.sell.enums;

import lombok.Getter;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/7
 */
@Getter
public enum ResultEnum implements CodeEnum {
    PRODUCT_NOT_EXIST(10, "商品不存在"),
    PRODUCT_STOCK_ERROR(11, "库存不足"),
    ORDER_NOT_EXIT(12, "订单不存在"),
    ORDER_DETAIL_NOT_EXIT(13, "订单详情不存在"),
    ORDER_STATUS_ERROR(14,"订单状态不正确"),
    ORDER_UPDATE_FAIL(15,"订单取消失败"),
    ORDER_CANCEL_SUCCESS(19,"订单取消成功"),
    ORDER_DETAIL_EMPTY(16,"订单详情不存在"),
    WX_PAY_NOTIFY_MONEY_VERIFY_ERROR(21, "微信支付异步通知金额校验不通过"),
    ;

    private Integer code;

    private String message;

    ResultEnum(Integer code, String message) {
    }
}
