package com.wong.store.order.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wong.store.common.exception.BusinessException;
import com.wong.store.feign.cart.CartFeignClient;
import com.wong.store.feign.product.ProductFeignClient;
import com.wong.store.feign.user.UserFeignClient;
import com.wong.store.model.dto.h5.OrderInfoDto;
import com.wong.store.model.entity.h5.CartInfo;
import com.wong.store.model.entity.order.OrderInfo;
import com.wong.store.model.entity.order.OrderItem;
import com.wong.store.model.entity.order.OrderLog;
import com.wong.store.model.entity.product.ProductSku;
import com.wong.store.model.entity.user.UserAddress;
import com.wong.store.model.entity.user.UserInfo;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.model.vo.h5.TradeVo;
import com.wong.store.order.mapper.OrderInfoMapper;
import com.wong.store.order.mapper.OrderItemMapper;
import com.wong.store.order.mapper.OrderLogMapper;
import com.wong.store.order.service.OrderInfoService;
import com.wong.store.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/30 21:34
 */
@Service
public class OrderInfoServiceImpl implements OrderInfoService {
    @Resource
    private CartFeignClient cartFeignClient;

    @Resource
    private ProductFeignClient productFeignClient;

    @Resource
    private UserFeignClient userFeignClient;

    @Resource
    private OrderInfoMapper orderInfoMapper;

    @Resource
    private OrderItemMapper orderItemMapper;

    @Resource
    private OrderLogMapper orderLogMapper;

    @Override
    public TradeVo queryTrade() {
        // 1.远程调用获取选中的购物车列表
        List<CartInfo> cartInfoList = cartFeignClient.queryAllChecked();

        // 2.将购物项数据转换成功订单明细数据
        List<OrderItem> orderItemList = new ArrayList<>();
        cartInfoList.forEach(cartInfo -> {
            OrderItem orderItem = new OrderItem();
            orderItem.setSkuId(cartInfo.getSkuId());
            orderItem.setSkuName(cartInfo.getSkuName());
            orderItem.setSkuNum(cartInfo.getSkuNum());
            orderItem.setSkuPrice(cartInfo.getCartPrice());
            orderItem.setThumbImg(cartInfo.getImgUrl());
            orderItemList.add(orderItem);
        });

        // 3.计算总金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }

        // 4.整理TradeVo对象并返回
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);
        return tradeVo;
    }

    /**
     * 提交订单
     *
     * @param orderInfoDto 参数对象
     * @return 订单id
     */

    @Transactional
    @Override
    public Long submitOrder(OrderInfoDto orderInfoDto) {
        // 数据校验
        List<OrderItem> orderItemList = orderInfoDto.getOrderItemList();
        if (CollectionUtils.isEmpty(orderItemList)) {
            throw new BusinessException(ResultCodeEnum.DATA_ERROR);
        }

        for (OrderItem orderItem : orderItemList) {
            ProductSku productSku = productFeignClient.querySkuBySkuId(orderItem.getSkuId());
            if (null == productSku) {
                throw new BusinessException(ResultCodeEnum.DATA_ERROR);
            }
            // 校验库存
            if (orderItem.getSkuNum() > productSku.getStockNum()) {
                throw new BusinessException(ResultCodeEnum.STOCK_LESS);
            }
        }

        // 构建订单数据，保存订单
        UserInfo userInfo = AuthContextUtil.getUserInfo();
        OrderInfo orderInfo = new OrderInfo();
        // 订单编号
        orderInfo.setOrderNo(String.valueOf(System.currentTimeMillis()));
        // 用户id
        orderInfo.setUserId(userInfo.getId());
        // 用户昵称
        orderInfo.setNickName(userInfo.getNickName());
        // 用户收货地址信息
        Long userAddressId = orderInfoDto.getUserAddressId();
        UserAddress userAddress = userFeignClient.queryUserAddressById(orderInfoDto.getUserAddressId());
        orderInfo.setReceiverName(userAddress.getName());
        orderInfo.setReceiverPhone(userAddress.getPhone());
        orderInfo.setReceiverTagName(userAddress.getTagName());
        orderInfo.setReceiverProvince(userAddress.getProvinceCode());
        orderInfo.setReceiverCity(userAddress.getCityCode());
        orderInfo.setReceiverDistrict(userAddress.getDistrictCode());
        orderInfo.setReceiverAddress(userAddress.getFullAddress());
        // 订单金额
        BigDecimal totalAmount = new BigDecimal(0);
        for (OrderItem orderItem : orderItemList) {
            totalAmount = totalAmount.add(orderItem.getSkuPrice().multiply(new BigDecimal(orderItem.getSkuNum())));
        }
        orderInfo.setTotalAmount(totalAmount);
        orderInfo.setCouponAmount(new BigDecimal(0));
        orderInfo.setOriginalTotalAmount(totalAmount);
        orderInfo.setFeightFee(orderInfoDto.getFeightFee());
        orderInfo.setPayType(2);
        orderInfo.setOrderStatus(0);
        orderInfoMapper.save(orderInfo);

        //保存订单明细
        for (OrderItem orderItem : orderItemList) {
            orderItem.setOrderId(orderInfo.getId());
            orderItemMapper.save(orderItem);
        }

        //记录日志
        OrderLog orderLog = new OrderLog();
        orderLog.setOrderId(orderInfo.getId());
        orderLog.setProcessStatus(0);
        orderLog.setNote("提交订单");
        orderLogMapper.save(orderLog);

        // 远程调用service-cart微服务接口清空购物车数据
        cartFeignClient.deleteChecked();

        // 返回订单id
        return orderInfo.getId();
    }

    /**
     * 根据id获取订单信息
     *
     * @param id 订单id
     * @return 订单信息
     */
    @Override
    public OrderInfo queryById(Long id) {
        return orderInfoMapper.queryById(id);
    }

    /**
     * 立即购买
     *
     * @param skuId skuId
     * @return TradeVo对象
     */
    @Override
    public TradeVo buy(Long skuId) {
        // 1.查询商品
        ProductSku productSku = productFeignClient.querySkuBySkuId(skuId);
        List<OrderItem> orderItemList = new ArrayList<>();
        OrderItem orderItem = new OrderItem();
        orderItem.setSkuId(skuId);
        orderItem.setSkuName(productSku.getSkuName());
        orderItem.setSkuNum(1);
        orderItem.setSkuPrice(productSku.getSalePrice());
        orderItem.setThumbImg(productSku.getThumbImg());
        orderItemList.add(orderItem);

        // 计算总金额
        BigDecimal totalAmount = productSku.getSalePrice();
        TradeVo tradeVo = new TradeVo();
        tradeVo.setTotalAmount(totalAmount);
        tradeVo.setOrderItemList(orderItemList);

        // 返回
        return tradeVo;
    }

    /**
     * 根据条件分页查询订单信息
     *
     * @param current     当前页
     * @param limit       每页显示条数
     * @param orderStatus 查询条件
     * @return 订单信息列表
     */
    @Override
    public PageInfo<OrderInfo> queryByOrderStatusByPage(Integer current, Integer limit, Integer orderStatus) {
        PageHelper.startPage(current, limit);
        Long userId = AuthContextUtil.getUserInfo().getId();
        // 1.获取订单列表
        List<OrderInfo> orderInfoList = orderInfoMapper.queryByOrderStatus(userId, orderStatus);
        // 2.获取每个订单的订单分项
        orderInfoList.forEach(orderInfo -> {
            List<OrderItem> orderItemList = orderItemMapper.queryByOrderId(orderInfo.getId());
            orderInfo.setOrderItemList(orderItemList);
        });
        return new PageInfo<>(orderInfoList);
    }

    /**
     * 根据订单编号查询订单信息
     *
     * @param orderNo 订单编号
     * @return 订单信息
     */
    @Override
    public OrderInfo queryByOrderNo(String orderNo) {
        OrderInfo orderInfo = orderInfoMapper.queryByOrderNo(orderNo);
        List<OrderItem> orderItemList = orderItemMapper.queryByOrderId(orderInfo.getId());
        orderInfo.setOrderItemList(orderItemList);
        return orderInfo;
    }
}
