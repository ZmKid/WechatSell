package com.zmkid.sell.converter;

import com.zmkid.sell.dto.OrderDTO;
import com.zmkid.sell.entity.OrderMaster;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author zhang man
 * @Description: 转换工具类
 * @date 2021/6/15
 */
public class OrderMasterToOrderDTO {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster, orderDTO);
        return orderDTO;
    }
    public  static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        return orderMasterList.stream().map(item -> convert(item)).collect(Collectors.toList());
    }

}
