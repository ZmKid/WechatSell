package com.zmkid.sell.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.zmkid.sell.entity.OrderDetail;
import com.zmkid.sell.enums.OrderStatusEnum;
import com.zmkid.sell.enums.PayStatusEnum;
import com.zmkid.sell.util.EnumUtil;
import com.zmkid.sell.util.serializer.DateLongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * @author zhang man
 * @Description:
 * @date 2021/6/7
 */
@Data
// 当某个属性值为null时，不返回给前端
// @JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    private  String orderId;

    private  String buyerName;

    private  String buyerPhone;

    private  String buyerAddress;

    /*卖家微信*/
    private  String buyerOpenid;

    /*订单总金额*/
    private BigDecimal orderAmount;

    /*订单状态，默认新下单*/
    private Integer orderStatus;

    /*支付状态，默认等待支付*/
    private Integer payStatus;

    @JsonSerialize(using = DateLongSerializer.class)
    private Date createTime;

    @JsonSerialize(using = DateLongSerializer.class)
    private Date updateTime;

    List<OrderDetail> orderDetailList;

    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum() {
        return EnumUtil.getCode(orderStatus, OrderStatusEnum.class);
    }

    @JsonIgnore
    public PayStatusEnum getPayStatusEnum() {
        return EnumUtil.getCode(payStatus, PayStatusEnum.class);
    }
}
