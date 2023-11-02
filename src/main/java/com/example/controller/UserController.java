package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.User;
import com.example.mapper.UserMapper;
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
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserMapper userMapper;

    @ApiOperation("查询所有用户")
    @GetMapping("/allUser")
    public R allUser(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("permissions","user");
        List<User> users = userMapper.selectList(wrapper);
        return R.ok().data("users",users);
    }

    @ApiOperation("查询所有管理员")
    @GetMapping("/selectRoot")
    public R selectRoot(){
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("permissions","root");
        List<User> users = userMapper.selectList(wrapper);
        return R.ok().data("users",users);
    }

    @ApiOperation("用户登录")
    @GetMapping("/login")
    public R hello(@RequestParam("phone") String phone, @RequestParam("password") String password){
        QueryWrapper<User> queryWrapperByPhone = new QueryWrapper<>();
        queryWrapperByPhone.eq("u_phone",phone);
        User user = userMapper.selectOne(queryWrapperByPhone);
        if (user!=null){
            QueryWrapper<User> queryWrapperUser = new QueryWrapper<>();
            queryWrapperUser.eq("u_phone",phone).eq("u_pwd",password);
            User selectOne = userMapper.selectOne(queryWrapperUser);
            if (selectOne!=null){
                return R.ok().data("user",user);
            }else {
                return R.error().data("msg","密码错误");
            }
        }else {
            return R.error().data("msg","该有该用户");
        }

    }

    @ApiOperation("根据账户查询用户")
    @GetMapping("/selectByPhone")
    public R selectByPhone(@RequestParam("phone") String phone){
        QueryWrapper<User> queryWrapperByPhone = new QueryWrapper<>();
        queryWrapperByPhone.eq("u_phone",phone);
        User user = userMapper.selectOne(queryWrapperByPhone);
        if (user!=null){
            return R.error().data("msg","该手机号已被注册");
        }else {
            return R.ok().data("ok","手机号可用");
        }

    }

    @ApiOperation("用户注册")
    @PostMapping(value = "/enroll",produces = {"application/json;charset=UTF-8;"})
    public R enroll(@RequestBody User user){
        if(user.getuPhone()!=null && user.getuPwd()!=null){
            QueryWrapper<User> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("u_phone",user.getuPhone());
            User selectOne = userMapper.selectOne(queryWrapper);
            if (selectOne!=null){
                return R.error().data("msg","该手机号已被注册");
            }else {
                userMapper.insert(user);
                return R.ok().data("msg","注册成功");
            }
        }else {
            return R.error().data("msg","请填写完整信息");
        }
    }

    @ApiOperation("用户注销")
    @GetMapping("/logout")
    public R logout(@RequestParam("userPhone") String userPhone,@RequestParam("userPwd") String userPwd){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u_phone",userPhone)
                    .eq("u_pwd",userPwd);
        int delete = userMapper.delete(queryWrapper);
        if (delete==0){
            return R.error().data("msg","注销失败，请和对您的信息");
        }else {
            return R.ok().data("msg","注销成功,希望再次遇见");
        }
    }

    @ApiOperation("用户恢复")
    @GetMapping("/recover")
    public R recover(@RequestParam("userPhone") String userPhone,@RequestParam("userPwd") String userPwd){
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u_phone",userPhone)
                    .eq("u_pwd",userPwd);
        User user = userMapper.selectOne(queryWrapper);
        if (user!=null){
            return R.error().data("msg","该用户很活跃,未销户");
        }else {
            userMapper.recoveryUser(userPhone, userPwd);
            return R.ok().data("msg","恢复成功");
        }
    }

    @ApiOperation("修改个人信息")
    @PostMapping(value = "/upUser",produces = {"application/json;charset=UTF-8;"})
    public R upUser(@RequestBody User user){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("u_name",user.getuName())
                .set("u_phone",user.getuPhone())
                .set("u_pwd",user.getuPwd())
                .eq("u_id",user.getuId());
        int update = userMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }

    //管理员功能区

    @ApiOperation("管理员修改用户余额")
    @GetMapping("/upBalance")
    public R upBalance(@RequestParam("uid") Integer uid,@RequestParam("balance") Integer balance){
        UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("u_balance",balance)
                .eq("u_id",uid);
        userMapper.update(null,updateWrapper);
        return R.ok().data("msg","修改ok");
    }

    @ApiOperation("管理员删除用户")
    @GetMapping("/remove/{uid}")
    public R remove(@PathVariable("uid") Integer uid){
        int i = userMapper.removeUserById(uid);
        if (i>0){
            return R.ok().data("msg","删除成功");
        }else {
            return R.error().data("msg","删除失败");
        }
    }
}
