package com.zmkid.sell.service.impl;

import cn.hutool.core.util.IdUtil;
import com.zmkid.sell.converter.OrderMasterToOrderDTO;
import com.zmkid.sell.dto.CarDTO;
import com.zmkid.sell.dto.OrderDTO;
import com.zmkid.sell.entity.OrderDetail;
import com.zmkid.sell.entity.OrderMaster;
import com.zmkid.sell.entity.ProductInfo;
import com.zmkid.sell.enums.OrderStatusEnum;
import com.zmkid.sell.enums.PayStatusEnum;
import com.zmkid.sell.enums.ResultEnum;
import com.zmkid.sell.exception.SellException;
import com.zmkid.sell.repository.OrderDetailRepository;
import com.zmkid.sell.repository.OrderMasterRepository;
import com.zmkid.sell.service.OrderService;
import com.zmkid.sell.service.PayService;
import com.zmkid.sell.service.ProductService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * @author zhang man
 * @Description: 订单impl
 * @date 2021/6/7
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService {


    private ProductService productService;

    private OrderDetailRepository orderDetailRepository;

    private OrderMasterRepository orderMasterRepository;

    private PayService payService;

    @Autowired
    public void setProductService(ProductService productService) {
        this.productService = productService;
    }

    @Autowired
    public void setOrderDetailRepository(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    @Autowired
    public void setOrderMasterRepository(OrderMasterRepository orderMasterRepository) {
        this.orderMasterRepository = orderMasterRepository;
    }

    @Autowired
    public void setPayService(PayService payService) {
        this.payService = payService;
    }

    @Override
    @Transactional
    public OrderDTO createOrderMaster(OrderDTO orderDTO) {
        String orderId = IdUtil.simpleUUID();
        BigDecimal orderAmount =new BigDecimal(BigInteger.ZERO);
        // List<CarDTO> carDTOLst = new ArrayList<>();
        // 查询商品（数量，价格）
        for (OrderDetail orderDetail: orderDTO.getOrderDetailList()) {
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            // 计算订单总价
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);
            orderDetail.setDetailId(IdUtil.simpleUUID());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            // 订单详情
            orderDetailRepository.save(orderDetail);
            // CarDTO carDTO = new CarDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
            // carDTOLst.add(carDTO);
        }
        // 写入订单
        OrderMaster orderMaster =new OrderMaster();
        orderDTO.setOrderId(orderId);
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        orderMasterRepository.save(orderMaster);
        //4.扣库存
            List<CarDTO> carDTOList = orderDTO.getOrderDetailList().stream().map(item -> new CarDTO(item.getProductId(), item.getProductQuantity())).collect(Collectors.toList());
        productService.decreaseStock(carDTOList);
        return orderDTO;
    }

    @Override
    public OrderDTO findOne(String orderId) {
        OrderMaster orderMaster = this.orderMasterRepository.findById(orderId).orElse(null);
        if(orderMaster == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIT);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDER_DETAIL_NOT_EXIT);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        Page<OrderMaster> orderMasterPage = this.orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTO.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        /* 判断订单状态 */
        if(Objects.equals(orderDTO, OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单] 订单状态不正确 ，orderId= {}, orderStatus ={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        /* 返还库存 */
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单] 订单中无商品详情， orderDTO = {}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CarDTO> carDTOList = orderDTO.getOrderDetailList().stream().map(item -> new CarDTO(item.getProductId(), item.getProductQuantity())).collect(Collectors.toList());
        this.productService.increaseStock(carDTOList);
        /* 已支付给用户退款 */
        if(Objects.equals(orderDTO.getPayStatus(), PayStatusEnum.SUCCESS.getCode())){
            payService.refund(orderDTO);
        }
        /* 修改订单状态 */
        BeanUtils.copyProperties(orderDTO, orderMaster);
        orderMaster.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        OrderMaster resOrder = this.orderMasterRepository.save(orderMaster);
        if(resOrder == null){
            log.error("[取消订单] 更新失败， orderMaster = {}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public OrderDTO finish(OrderDTO orderDTO) {
        if(!Objects.equals(orderDTO.getOrderStatus(),OrderStatusEnum.NEW.getCode())){
            log.error("报错");
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        this.orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    public OrderDTO paid(OrderDTO orderDTO) {
        // 判断订单状态
        if (!Objects.equals(orderDTO.getOrderStatus(), OrderStatusEnum.NEW.getCode())) {
            log.error("支付完成");
            throw new SellException(12,"订单状态错误");
        }

        // 判断支付状态
        if (Objects.equals(orderDTO.getPayStatus(), PayStatusEnum.SUCCESS.getCode())) {
            log.error("支付完成");
            throw new SellException(12,"订单状态错误");
        }

        // 修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO, orderMaster);
        this.orderMasterRepository.save(orderMaster);
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = this.orderMasterRepository.findAll(pageable);
        List<OrderDTO> orderDTOList = OrderMasterToOrderDTO.convert(orderMasterPage.getContent());
        return new PageImpl<>(orderDTOList, pageable, orderMasterPage.getTotalElements());
    }
}
