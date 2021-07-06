package com.zmkid.sell.entity;

import com.zmkid.sell.enums.OrderStatusEnum;
import com.zmkid.sell.enums.PayStatusEnum;
import com.zmkid.sell.enums.ProductStatusEnum;
import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @author zhang man
 * @Description: 订单
 * @date 2021/6/1
 */
@Data
@Entity
public class OrderMaster {

    @Id
    private String orderId;

    private String buyerName;

    private String buyerPhone;

    private String buyerAddress;

    /*卖家微信*/
    private String buyerOpenid;

    /*订单总金额*/
    private BigDecimal orderAmount;

    /*订单状态，默认新下单*/
    private Integer orderStatus;

    /*支付状态，默认等待支付*/
    private Integer payStatus;

    private Date createTime;

    private Date updateTime;


}
