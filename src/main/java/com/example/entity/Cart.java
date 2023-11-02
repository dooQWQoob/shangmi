package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Arrays;

/**
 * <p>
 * 
 * </p>
 *
 * @author taozi
 * @since 2023-10-07
 */
@TableName("t_cart")
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 购物车id
     */
    @TableId(value = "cart_id", type = IdType.AUTO)
    private Integer cartId;

    /**
     * 用户id
     */
    private Integer uId;

    /**
     * 商品id
     */
    private Integer goodsId;

    /**
     * 商品名称
     */
    private String goodsName;

    /**
     * 商品图片
     */
    private byte[] goodsImage;

    /**
     * 商品规格
     */
    private String goodsSpecs;

    /**
     * 商品价格
     */
    private Double goodsPrice;

    /**
     * 商品数量
     */
    private Integer goodsQty;

    /**
     * 商品总价
     */
    private Double goodsSum;

    /**
     * 商品介绍
     */
    private String goodsIntroduce;

    /**
     *下单状态
     * @return
     */
    private Integer isPay;

    /**
     * 逻辑删除，当用户下顶单后，删除，但在订单查询还需要
     */
    @TableLogic
    private Integer isDelete;

    public Integer getCartId() {
        return cartId;
    }

    public void setCartId(Integer cartId) {
        this.cartId = cartId;
    }
    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }
    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }
    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
    public byte[] getGoodsImage() {
        return goodsImage;
    }

    public void setGoodsImage(byte[] goodsImage) {
        this.goodsImage = goodsImage;
    }
    public String getGoodsSpecs() {
        return goodsSpecs;
    }

    public void setGoodsSpecs(String goodsSpecs) {
        this.goodsSpecs = goodsSpecs;
    }
    public Double getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Double goodsPrice) {
        this.goodsPrice = goodsPrice;
    }
    public Integer getGoodsQty() {
        return goodsQty;
    }

    public void setGoodsQty(Integer goodsQty) {
        this.goodsQty = goodsQty;
    }
    public Double getGoodsSum() {
        return goodsSum;
    }

    public void setGoodsSum(Double goodsSum) {
        this.goodsSum = goodsSum;
    }

    public String getGoodsIntroduce() {
        return goodsIntroduce;
    }

    public void setGoodsIntroduce(String goodsIntroduce) {
        this.goodsIntroduce = goodsIntroduce;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }

    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cartId=" + cartId +
                ", goodsId=" + goodsId +
                ", goodsImage=" + Arrays.toString(goodsImage) +
                ", goodsIntroduce='" + goodsIntroduce + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsQty=" + goodsQty +
                ", goodsSpecs='" + goodsSpecs + '\'' +
                ", goodsSum=" + goodsSum +
                ", isDelete=" + isDelete +
                ", isPay=" + isPay +
                ", uId=" + uId +
                '}';
    }
}
