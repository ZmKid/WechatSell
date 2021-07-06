package com.zmkid.sell.controller;

import com.zmkid.sell.dto.OrderDTO;
import com.zmkid.sell.service.OrderService;
import com.zmkid.sell.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author zhang man
 * @Description: 测试微信支付
 * @date 2021/7/2
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class PayControllerTest {

    private OrderService orderService;

    private PayService payService;

    @Autowired
    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }

    @Test
    public void  testCreate(){
        OrderDTO orderDTO = orderService.findOne("e2f7fbb3df234fa58fe725baecf42we");
        payService.create(orderDTO);
    }

    @Test
    public void testCancel(){
        OrderDTO orderDTO = orderService.findOne("08962d6e8b1f4fadb155c1c147a97f5a");
        payService.refund(orderDTO);
    }


}