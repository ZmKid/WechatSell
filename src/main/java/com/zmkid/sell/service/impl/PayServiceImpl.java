package com.zmkid.sell.service.impl;

import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import com.lly835.bestpay.utils.JsonUtil;
import com.zmkid.sell.dto.OrderDTO;
import com.zmkid.sell.enums.ResultEnum;
import com.zmkid.sell.exception.SellException;
import com.zmkid.sell.service.PayService;
import com.zmkid.sell.util.SellMathUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zhang man
 * @Description: 支付
 * @date 2021/6/30
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService {

    private static final String ORDER_NAME = "微信点餐订单";

    private BestPayServiceImpl bestPayService;

//    private WxPayH5Config wxPayH5Config;

//    @Autowired
//    public void setWxPayH5Config(WxPayH5Config wxPayH5Config) {
//        this.wxPayH5Config = wxPayH5Config;
//    }

    private OrderServiceImpl orderService;

    @Autowired
    public void setOrderService(OrderServiceImpl orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setBestPayService(BestPayServiceImpl bestPayService) {
        this.bestPayService = bestPayService;
    }

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);
        log.info("【微信支付】发起微信支付，request ={}", JsonUtil.toJson(payRequest));
        // bestPayService.setWxPayH5Config(wxPayH5Config);
        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("【微信支付】发起微信支付，response ={}", JsonUtil.toJson(payResponse));
        return payResponse;
    }

    @Override
    public PayResponse notify(String notifyData) {
        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("【微信支付】 异步通知， payResponse={}", JsonUtil.toJson(payResponse));
        OrderDTO order = orderService.findOne(payResponse.getOrderId());
        if(order == null){
            log.error("【微信支付】异步通知，订单不存 在， orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        if(!SellMathUtil.equals(payResponse.getOrderAmount(), order.getOrderAmount().doubleValue())){
            log.error("【微信支付】异步通知，订单金额不一致看, orderId = {}, 微信通知金额={}， 系统金额={}",
                    payResponse.getOrderId(),+
                    payResponse.getOrderAmount(),
                    order.getOrderAmount()
            );
            throw new SellException(ResultEnum.WX_PAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        // 修改订单支付状态
        orderService.paid(order);
        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     * @return
     */
    public RefundResponse refund(OrderDTO orderDTO){
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_MP);
        log.info("【微信退款】request={}", refundRequest);
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("【微信退款】 response={}", refundResponse);
        return refundResponse;
    }
}
