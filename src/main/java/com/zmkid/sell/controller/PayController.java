package com.zmkid.sell.controller;

import com.lly835.bestpay.model.PayResponse;
import com.zmkid.sell.dto.OrderDTO;
import com.zmkid.sell.enums.ResultEnum;
import com.zmkid.sell.exception.SellException;
import com.zmkid.sell.service.OrderService;
import com.zmkid.sell.service.PayService;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/**
 * @author zhang man
 * @Description: 支付
 * @date 2021/6/30
 */
@Controller
@RequestMapping("/pay")
@Slf4j
public class PayController {

    private OrderService orderService;

    private PayService payService;

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Autowired
    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId")String orderId,
                               @RequestParam("returnUrl")String returnUrl,
                               Map<String, Object> map){
        // 查询订单
        OrderDTO order = this.orderService.findOne(orderId);
        if(order == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        String url = "http://zmsell.natapp1.cc/sell/pay/create?orderId="+orderId+"&returnUrl=http://sell.com/#/order/"+orderId;
        log.info("手机支付连接，{}",url);
        // 发起支付
        PayResponse payResponse = payService.create(order);
        map.put("payResponse", payResponse);
        map.put("returnUrl", returnUrl);
         return new ModelAndView("pay/create", map);
    }

    /**
     * 微信异步支付
     * @param notifyData 验证支付数据
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        payService.notify(notifyData);
        // 返回给微信处理结果 返回给微信处理结果
        return new ModelAndView("pay/success");
    }
}
