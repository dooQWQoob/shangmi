package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Address;
import com.example.mapper.AddressMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author taozi
 * @since 2023-10-07
 */
@RestController
@CrossOrigin
@RequestMapping("/address")
public class AddressController {
    @Autowired
    private AddressMapper addressMapper;

    @ApiOperation("返回所有用户地址")
    @GetMapping("/selectAllAddress")
    public R selectAllAddress(){
        List<Address> addresses = addressMapper.selectList(null);
        return R.ok().data("addresses",addresses);
    }

    @ApiOperation("根据用户id添收货信息")
    @PostMapping(value = "/addUserAddress",produces = {"application/json;charset=UTF-8;"})
    public R addUserAddress(@RequestBody Address address){
        if ("".equals(address.getuName()) || "".equals(address.getuPhone()) || "".equals(address.getAddress()) || address.getuId()==null){
            return R.error().data("msg","请填写完整信息");
        }else {
            QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("u_name",address.getuName())
                        .eq("u_phone",address.getuPhone())
                        .eq("address",address.getAddress());
            Address selectOne = addressMapper.selectOne(queryWrapper);
            if (selectOne!=null){
                return R.error().data("err","您已添加该收货信息,请不要过多添加");
            }else {
                addressMapper.insert(address);
                return R.ok().data("msg","添加成功");
            }
        }

    }

    @ApiOperation("根据用户id修改收货信息")
    @PostMapping(value = "/upAddressById",produces = {"application/json;charset=UTF-8;"})
    public R upAddressById(@RequestBody Address address){
        UpdateWrapper<Address> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("u_name",address.getuName())
                .set("u_phone",address.getuPhone())
                .set("address",address.getAddress())
                .eq("u_id",address.getuId())
                .eq("address_id",address.getAddressId());
        int update = addressMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }

    @ApiOperation("根据用户id查询收货信息")
    @GetMapping("/selectByUserId/{uid}")
    public R selectByUserId(@PathVariable("uid") Integer uid){
        QueryWrapper<Address> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u_id",uid);
        List<Address> addresses = addressMapper.selectList(queryWrapper);
        return R.ok().data("addresses",addresses);
    }

    @ApiOperation("根据id删除收货信息")
    @GetMapping("/deleteById/{addressId}")
    public R deleteById(@PathVariable("addressId") Integer addressId){
        addressMapper.deleteById(addressId);
        return R.ok().data("msg","删除成功");
    }

}
