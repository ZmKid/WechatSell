package com.zmkid.sell.service;

import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;
import com.zmkid.sell.dto.OrderDTO;

/**
 * @author zhang man
 * @Description: 支付
 * @date 2021/6/30
 */
public interface PayService {

    PayResponse create(OrderDTO orderDTO);

    PayResponse notify(String notifyData);

    RefundResponse refund(OrderDTO orderDTO);
}
