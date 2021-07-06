package com.zmkid.sell.service;

import com.zmkid.sell.dto.OrderDTO;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/17
 */
public interface BuyerService {
    //查询一个订单
    OrderDTO findOrderOne(String openid, String orderId);

    //取消订单
    OrderDTO cancelOrder(String openid, String orderId);
}
