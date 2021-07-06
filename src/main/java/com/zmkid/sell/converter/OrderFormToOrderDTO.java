package com.zmkid.sell.converter;

import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zmkid.sell.dto.OrderDTO;
import com.zmkid.sell.entity.OrderDetail;
import com.zmkid.sell.exception.SellException;
import com.zmkid.sell.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.description.method.MethodDescription;

import java.util.ArrayList;
import java.util.List;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/16
 */
@Slf4j
public class OrderFormToOrderDTO {

    public static OrderDTO convert(OrderForm orderForm){
        Gson gson = new Gson();
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());
        List<OrderDetail> orderDetailList;
        try {
            orderDetailList = gson.fromJson(orderForm.getItems(), new TypeToken<List<OrderDetail>>() {
            }.getType());
        }catch (Exception e){
            log.error("对象转换错误");
            throw new SellException(16,"参数不正确");
        }
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }
}
