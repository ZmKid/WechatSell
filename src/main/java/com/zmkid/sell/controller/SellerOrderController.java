package com.zmkid.sell.controller;

import com.zmkid.sell.dto.OrderDTO;
import com.zmkid.sell.enums.ResultEnum;
import com.zmkid.sell.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * @author zhang man
 * @Description: 卖家controller
 * @date 2021/7/7
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    private OrderService orderService;

    @GetMapping("/list")
    public ModelAndView findList(@RequestParam( defaultValue = "10") int pageSize,
                                 @RequestParam(defaultValue = "1") int pageNo,
                                 Map<String, Object> map){
        PageRequest pageRequest = PageRequest.of(pageNo - 1,pageSize);
        Page<OrderDTO> pageList = orderService.findList(pageRequest);
        map.put("orderDTOPage", pageList);
        map.put("currentPage", pageNo);
        map.put("size", pageSize);
        return new ModelAndView("/order/list",map);
    }
    @GetMapping("/detail")
    public ModelAndView getDetail(@RequestParam(value = "orderId") String orderId,
                          Map<String, Object> map){
        OrderDTO orderDTO = new OrderDTO();
        try{
            orderDTO = orderService.findOne(orderId);
        }catch (Exception e){
            map.put("msg", e);
            map.put("url", "sell/seller/order/list");
            return new ModelAndView("/common/error", map);
        }
        map.put("orderDTO",orderDTO);
        return new ModelAndView("/order/detail", map);
    }

    @GetMapping("/cancel")
    public ModelAndView cancel(@RequestParam(value = "orderId") String orderId,
                               Map<String, Object> map){

        try{
            OrderDTO orderDTO = orderService.findOne(orderId);
            orderService.cancel(orderDTO);
        }catch (Exception e){
            map.put("msg", e);
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("msg", ResultEnum.ORDER_CANCEL_SUCCESS);
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("/common/success", map);

    }

    @Autowired
    public void setOrderService(OrderService orderService) {
        this.orderService = orderService;
    }
}
