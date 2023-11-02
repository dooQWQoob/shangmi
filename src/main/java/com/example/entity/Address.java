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
@TableName("t_address")
public class Address implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 地址id
     */
    @TableId(value = "address_id", type = IdType.AUTO)
    private Integer addressId;

    /**
     * 用户id
     */
    private Integer uId;

    /**
     * 用户姓名
     */
    private String uName;

    /**
     * 用户电话
     */
    private String uPhone;

    /**
     * 用户地址
     */
    private String address;

    public Integer getAddressId() {
        return addressId;
    }

    public void setAddressId(Integer addressId) {
        this.addressId = addressId;
    }
    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }
    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }
    public String getuPhone() {
        return uPhone;
    }

    public void setuPhone(String uPhone) {
        this.uPhone = uPhone;
    }
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Address{" +
            "addressId=" + addressId +
            ", uId=" + uId +
            ", uName=" + uName +
            ", uPhone=" + uPhone +
            ", address=" + address +
        "}";
    }
}
