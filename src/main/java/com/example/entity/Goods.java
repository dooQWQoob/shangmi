package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
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
@TableName("t_goods")
public class Goods implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    @TableId(value = "goods_id", type = IdType.AUTO)
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
     * 商品库存
     */
    private Integer goodsQty;

    /**
     * 商品类别
     */
    private String goodsClass;

    /**
     * 商品介绍
     */
    private String goodsIntroduce;

    /**
     * 销量
     */
    private Integer sales;

    /**
     * 是否加入今日推荐 0/1
     *
     */
    private Short recommended;

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
    public String getGoodsClass() {
        return goodsClass;
    }

    public void setGoodsClass(String goodsClass) {
        this.goodsClass = goodsClass;
    }
    public String getGoodsIntroduce() {
        return goodsIntroduce;
    }

    public void setGoodsIntroduce(String goodsIntroduce) {
        this.goodsIntroduce = goodsIntroduce;
    }

    public Integer getSales() {
        return sales;
    }

    public void setSales(Integer sales) {
        this.sales = sales;
    }

    public Short getRecommended() {
        return recommended;
    }

    public void setRecommended(Short recommended) {
        this.recommended = recommended;
    }

    @Override
    public String toString() {
        return "Goods{" +
                "goodsClass='" + goodsClass + '\'' +
                ", goodsId=" + goodsId +
                ", goodsImage=" + Arrays.toString(goodsImage) +
                ", goodsIntroduce='" + goodsIntroduce + '\'' +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsQty=" + goodsQty +
                ", goodsSpecs='" + goodsSpecs + '\'' +
                ", recommended=" + recommended +
                ", sales=" + sales +
                '}';
    }
}
