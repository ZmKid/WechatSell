package com.zmkid.sell.controller;

import com.zmkid.sell.converter.OrderFormToOrderDTO;
import com.zmkid.sell.dto.OrderDTO;
import com.zmkid.sell.exception.SellException;
import com.zmkid.sell.form.OrderForm;
import com.zmkid.sell.service.BuyerService;
import com.zmkid.sell.service.OrderService;
import com.zmkid.sell.util.ResultVoUtil;
import com.zmkid.sell.vo.ResultVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.util.CollectionUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.*;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/15
 */
@RestController
@RequestMapping("/buyer/order")
@Slf4j
public class BuyerOrderController {

    @Autowired
    private OrderService orderService;

    @Autowired
    private BuyerService buyerService;

    // 创建订单
    @PostMapping("/create")
    public ResultVO<Map<String, String>> create(@Valid OrderForm orderForm, BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            log.error("参数不正确");
            throw new SellException(16, Objects.requireNonNull(bindingResult.getFieldError()).getDefaultMessage());
        }
        OrderDTO orderDTO = OrderFormToOrderDTO.convert(orderForm);
        orderDTO.setBuyerOpenid("oRCtv1Zf3v4YRk4MFFMdmFk3gZHk");
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("购物车不能为空");
            throw new SellException(13, "购物车不能为空");
        }

        OrderDTO orderMaster = orderService.createOrderMaster(orderDTO);
        Map<String, String> map = new HashMap<>();
        map.put("orderId" , orderMaster.getOrderId());
        ResultVO<Map<String,String>> resVO = new ResultVO<>();
        resVO.setCode(0);
        resVO.setMsg("success");
        resVO.setData(map);
        return resVO;
    }

    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam String openId,
                                         @RequestParam(defaultValue = "0") Integer page,
                                         @RequestParam(defaultValue = "10") Integer size) {
        if(Strings.isBlank(openId)){
            log.error("参数错误");
            throw  new SellException(15,"参数错误");
        }
        Pageable pageable = PageRequest.of(page - 1, size);
        Page<OrderDTO> list = orderService.findList(openId, pageable);
        return ResultVoUtil.success(list.getContent());
    }

    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId) {
        String openId = "oRCtv1Zf3v4YRk4MFFMdmFk3gZHk";
        OrderDTO orderDTO = buyerService.findOrderOne(openId, orderId);
        return ResultVoUtil.success( orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId) {
        OrderDTO orderDTO = buyerService.cancelOrder(openid, orderId);
        return ResultVoUtil.success(orderDTO);
    }

}
