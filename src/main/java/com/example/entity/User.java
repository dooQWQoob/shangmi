package com.example.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * <p>
 * 
 * </p>
 *
 * @author taozi
 * @since 2023-10-07
 */
@TableName("t_user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户id
     */
    @TableId(value = "u_id", type = IdType.AUTO)
    private Integer uId;

    /**
     * 用户名称
     */
    private String uName;

    /**
     * 用户账户
     */
    private String uPhone;

    /**
     * 用户密码
     */
    private String uPwd;

    /**
     * 权限
     */
    private String permissions;

    /**
     * 用户创建时间
     */
    private String uCreatedate;

    /**
     * 用户余额
     */
    private Double uBalance;

    /**
     * 逻辑删除
     */
    @TableLogic
    private Integer isDelete;

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
    public String getuPwd() {
        return uPwd;
    }

    public void setuPwd(String uPwd) {
        this.uPwd = uPwd;
    }
    public String getPermissions() {
        return permissions;
    }

    public void setPermissions(String permissions) {
        this.permissions = permissions;
    }
    public String getuCreatedate() {
        return uCreatedate;
    }

    public void setuCreatedate(String uCreatedate) {
        this.uCreatedate = uCreatedate;
    }
    public Double getuBalance() {
        return uBalance;
    }

    public void setuBalance(Double uBalance) {
        this.uBalance = uBalance;
    }
    public Integer getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Integer isDelete) {
        this.isDelete = isDelete;
    }

    @Override
    public String toString() {
        return "User{" +
            "uId=" + uId +
            ", uName=" + uName +
            ", uPhone=" + uPhone +
            ", uPwd=" + uPwd +
            ", permissions=" + permissions +
            ", uCreatedate=" + uCreatedate +
            ", uBalance=" + uBalance +
            ", isDelete=" + isDelete +
        "}";
    }
}
