package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Cart;
import com.example.entity.Goods;
import com.example.mapper.CartMapper;
import com.example.mapper.GoodsMapper;
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
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @ApiOperation("添加购物车")
    @PostMapping(value = "/addCart",produces = {"application/json;charset=UTF-8;"})
    public R addCart(@RequestBody Cart cart){
        //查询所对应的商品，进行商品减
        QueryWrapper<Goods> goodsQW = new QueryWrapper<>();
        goodsQW.eq("goods_id",cart.getGoodsId());
        Goods goods = goodsMapper.selectOne(goodsQW);
        if (goods.getGoodsQty()-cart.getGoodsQty()<0){
            return R.error().data("msg","加入失败,该商品库存不足");
        }
        UpdateWrapper<Goods> goodsUpdate = new UpdateWrapper<>();
        goodsUpdate.set("goods_qty",goods.getGoodsQty()-cart.getGoodsQty()).eq("goods_id",goods.getGoodsId());
        goodsMapper.update(null, goodsUpdate);
        cartMapper.insert(cart);
        return R.ok().data("msg","加入购物车成功");
    }

    @ApiOperation("根据id删除购物车")
    @GetMapping("/removeCartById/{cartId}")
    public R removeCartById(@PathVariable("cartId") Integer cartId){
        //根据购物车id查询该购物车
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("cart_id",cartId);
        Cart cart = cartMapper.selectOne(queryWrapper);
        //查询所对应的商品，进行商品恢复
        QueryWrapper<Goods> goodsQW = new QueryWrapper<>();
        goodsQW.eq("goods_id",cart.getGoodsId());
        Goods goods = goodsMapper.selectOne(goodsQW);
        UpdateWrapper<Goods> goodsUpdate = new UpdateWrapper<>();
        goodsUpdate.set("goods_qty",goods.getGoodsQty()+cart.getGoodsQty()).eq("goods_id",goods.getGoodsId());
        goodsMapper.update(null,goodsUpdate);
        int i = cartMapper.removeCartById(cartId);
        if (i>0){
            return R.ok().data("msg","删除成功");
        }else {
            return R.error().data("msg","删除失败");
        }
    }

    @ApiOperation("根据用户id查询购物车")
    @GetMapping("/selectCartByUid")
    public R selectCartByUid(@RequestParam Integer userId, @RequestParam Integer current){
        //设置分页
        Page<Cart> page = new Page<>(current,5);
        //分页查询根据id
        QueryWrapper<Cart> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u_id",userId);
        cartMapper.selectPage(page, queryWrapper);
        //统计用户购物车数量
        long pages = cartMapper.countByUid(userId);
        //返回的当前页的数据
        List<Cart> carts = page.getRecords();
        if (carts.size()>0){
            return R.ok().data("carts",carts).data("pages",pages);
        }else {
            return R.error().data("msg","啊哦,购物车为空!");
        }
    }

    @ApiOperation("根据用户id统计购物车")
    @GetMapping("/countByUid/{uid}")
    public R countByUid(@PathVariable("uid") Integer uid){
        int i = cartMapper.countByUid(uid);
        return R.ok().data("cartSum",i);
    }

    @ApiOperation("根据商品id修改商品数量并修改下单状态")
    @GetMapping("/upQtyByGoodsid")
    public R upQtyByGoodsid(@RequestParam("qty") Integer qty,@RequestParam("isPay") Integer isPay,@RequestParam("cartid") Integer cartid){
        UpdateWrapper<Cart> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("goods_qty",qty)
                .set("is_pay",isPay)
                .eq("cart_id",cartid);
        int update = cartMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","修改数量成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }

    @ApiOperation("根据用户id查询下单状态的购物车")
    @GetMapping("/selectIspayByUserid")
    public R selectIspayByUserid(@RequestParam("uid") Integer uid){
        QueryWrapper<Cart> wrapper = new QueryWrapper<>();
        wrapper.eq("is_pay",1).eq("u_id",uid);
        List<Cart> carts = cartMapper.selectList(wrapper);
        return R.ok().data("carts",carts);
    }
}
