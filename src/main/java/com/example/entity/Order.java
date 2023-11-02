package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * <p>
 * 
 * </p>
 *
 * @author taozi
 * @since 2023-10-07
 */
@TableName("t_order")
public class Order implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 订单id
     */
    @TableId(value = "order_id", type = IdType.AUTO)
    private Integer orderId;

    /**
     * 购物车id,根据id查询有关购物车
     */
    private String cartIds;

    /**
     * 用户id
     */
    private Integer uId;

    /**
     * 订单状态
     * 0 : 未付款
     * 1 : 已付款
     * 2 : 配送中
     * 3 : 已完成
     */
    private String state;

    /**
     * 支付方式
     */
    private String payway;

    /**
     * 创建时间
     */
    private String createTime;

    /**
     * 地址id
     */
    private Integer addressId;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }
    public String getCartIds() {
        return cartIds;
    }

    public void setCartIds(String cartIds) {
        this.cartIds = cartIds;
    }
    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
    public String getPayway() {
        return payway;
    }

    public void setPayway(String payway) {
        this.payway = payway;
    }
    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }

    @Override
    public String toString() {
        return "Order{" +
                "addressId=" + addressId +
                ", cartIds='" + cartIds + '\'' +
                ", createTime='" + createTime + '\'' +
                ", orderId=" + orderId +
                ", payway='" + payway + '\'' +
                ", state='" + state + '\'' +
                ", uId=" + uId +
                '}';
    }
}
