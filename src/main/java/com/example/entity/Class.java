package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author taozi
 * @since 2023-10-07
 */
@TableName("t_class")
public class Class implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 类别id
     */
    @TableId(value = "class_id", type = IdType.AUTO)
    private Integer classId;

    /**
     * 商品类别名称
     */
    private String goodsClass;

    public Integer getClassId() {
        return classId;
    }

    public void setClassId(Integer classId) {
        this.classId = classId;
    }
    public String getGoodsClass() {
        return goodsClass;
    }

    public void setGoodsClass(String goodsClass) {
        this.goodsClass = goodsClass;
    }

    @Override
    public String toString() {
        return "Class{" +
            "classId=" + classId +
            ", goodsClass=" + goodsClass +
        "}";
    }
}
