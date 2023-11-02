package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.*;
import com.example.mapper.*;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

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
@RequestMapping("/order")
public class OrderController {
    @Autowired
    private OrderMapper orderMapper;

    @Autowired
    private CartMapper cartMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private GoodsMapper goodsMapper;

    @Autowired
    private AddressMapper addressMapper;

    @ApiOperation("查询所有订单")
    @GetMapping("/selectAllOrder")
    public R selectAllOrder(){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("create_time");
        List<Order> orders = orderMapper.selectList(wrapper);
        return R.ok().data("orders",orders);
    }

    @ApiOperation("查询已付款订单")
    @GetMapping("/selectOrderState1")
    public R selectOrderState1(){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("state",1).orderByDesc("create_time");
        List<Order> orders = orderMapper.selectList(wrapper);
        return R.ok().data("orders",orders);
    }

    @ApiOperation("查询配送中订单")
    @GetMapping("/selectOrderState2")
    public R selectOrderState2(){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("state",2).orderByDesc("create_time");
        List<Order> orders = orderMapper.selectList(wrapper);
        return R.ok().data("orders",orders);
    }

    @ApiOperation("查询已完成订单")
    @GetMapping("/selectOrderState3")
    public R selectOrderState3(){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("state",3).orderByDesc("create_time");
        List<Order> orders = orderMapper.selectList(wrapper);
        return R.ok().data("orders",orders);
    }

    @ApiOperation("添加订单")
    @PostMapping(value = "/addOrders",produces = {"application/json;charset=UTF-8;"})
    public R addOrders(@RequestBody Order order){
        String[] arr = order.getCartIds().split(",");
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0;i<arr.length;i++){
            list.add(Integer.valueOf(arr[i]));
        }
        //根据订单查询购物车
        List<Cart> carts = cartMapper.selectBatchIds(list);
        //将购物车金额求和
        double sum = 0;
        for (int i=0;i<carts.size();i++){
            sum+=carts.get(i).getGoodsQty()*carts.get(i).getGoodsPrice();
        }
        User user = userMapper.selectById(order.getuId());
        //判断用户余额是否满足
        if (user.getuBalance()-sum>=0){
            //计数，当所有商品能进行减库，才进行购买
            int jishu=0;
            //string的商品ids
            String goodsids="";
            //商品库存减，销量加
            for (int i=0;i<carts.size();i++){
                //根据购物车的id查询商品
                QueryWrapper<Goods> goodsQW = new QueryWrapper<>();
                goodsQW.eq("goods_id",carts.get(i).getGoodsId());
                Goods goods = goodsMapper.selectOne(goodsQW);
                if (i==0){
                    goodsids+=goods.getGoodsId();
                }else {
                    goodsids+=","+goods.getGoodsId();
                }

                //商品库存满足计数
                if (goods.getGoodsQty()-carts.get(i).getGoodsQty()>=0){
                    jishu++;
                }else {
                    //商品库存不足无法购买
                    return R.error().data("err","无法购买,有商品库存不足,请联系工作人员添加");
                }
                //当循环完毕，相等表示都能够减库，再次循环，进行减库
                if (jishu==carts.size()){
                    for (int b=0;b<carts.size();b++){
                        //修改商品库存与销量
                        UpdateWrapper<Goods> goodsUp = new UpdateWrapper<>();
                        goodsUp.set("goods_qty",goods.getGoodsQty()-carts.get(b).getGoodsQty())
                                .set("sales",goods.getSales()+carts.get(b).getGoodsQty())
                                .eq("goods_id",goods.getGoodsId());
                        goodsMapper.update(null,goodsUp);
                        //删除对应购物车
                        cartMapper.deleteById(carts.get(b).getCartId());
                    }
                }
            }
            //钱足够,扣除订单总值
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("u_balance",user.getuBalance()-sum).eq("u_id",user.getuId());
            userMapper.update(null,updateWrapper);
            //添加订单
            orderMapper.insert(order);
            return R.ok().data("msg","下单成功");
        }else {
            //钱不够
            return R.error().data("err","您的余额不足,无法购买");
        }
    }

    @ApiOperation("根据订单id查询")
    @GetMapping("/selectByOrderId")
    public R selectByOrderId(@RequestParam("oid") Integer oid){
        System.out.println(oid);
        Order order = orderMapper.selectById(oid);
        //将字符串分割成字符串数组
        String[] arr = order.getCartIds().split(",");
        //购物车列表
        ArrayList<Cart> carts = new ArrayList<>();
        for (int i=0;i<arr.length;i++){
            carts.add(cartMapper.selectByCartId(Integer.valueOf(arr[i])));
        }
        //地址
        Address address = addressMapper.selectById(order.getAddressId());
        if (address==null){
            Address address1 = new Address();
            address1.setuName("");
            address1.setuPhone("");
            address1.setAddress("");
            return R.ok().data("order",order).data("carts",carts).data("address",address1);
        }
        return R.ok().data("order",order).data("carts",carts).data("address",address);
    }

    @ApiOperation("根据用户id查询所有订单")
    @GetMapping("/selectOrderByUid")
    public R selectOrderByUid(@RequestParam("uid") Integer uid){
        QueryWrapper<Order> wrapper = new QueryWrapper<>();
        wrapper.eq("u_id",uid).orderByDesc("create_time");
        List<Order> orders = orderMapper.selectList(wrapper);

        //购物车列表
//        ArrayList<Cart> carts = new ArrayList<>();
//        ArrayList<List> list = new ArrayList<>();
//        orders.forEach((o)->{
//            String[] arr = o.getCartIds().split(",");
//            for (int b=0;b<arr.length;b++){
//                carts.add(cartMapper.selectByCartId(Integer.valueOf(arr[b])));
//
//            }
//            list.add(carts);
//        });
        return R.ok().data("order",orders);
    }

    @ApiOperation("用户下单并付款")
    @GetMapping("/payOrder/{oid}")
    public R payOrder(@PathVariable("oid") Integer oid){
        //查询该订单
        Order order = orderMapper.selectById(oid);
        String[] arr = order.getCartIds().split(",");
        ArrayList<Integer> list = new ArrayList<>();
        for (int i=0;i<arr.length;i++){
            list.add(Integer.valueOf(arr[i]));
        }
        //根据订单查询购物车
        List<Cart> carts = cartMapper.selectBatchIds(list);
        double sum = 0;
        //将购物车金额求和
        for (int i=0;i<carts.size();i++){
            sum+=carts.get(i).getGoodsQty()*carts.get(i).getGoodsPrice();
        }
        User user = userMapper.selectById(order.getuId());
        if (user.getuBalance()-sum>=0){
            //钱足够
            //修改用户余额
            UpdateWrapper<User> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("u_balance",user.getuBalance()-sum).eq("u_id",user.getuId());
            userMapper.update(null,updateWrapper);
            //计数，当所有商品能进行减库，才进行购买
            int jishu=0;
            //商品库存减，销量加
            for (int i=0;i<carts.size();i++){
                //根据购物车的商品id查询商品
                QueryWrapper<Goods> goodsQW = new QueryWrapper<>();
                goodsQW.eq("goods_id",carts.get(i).getGoodsId());
                Goods goods = goodsMapper.selectOne(goodsQW);

                //库存满足计数
                if (goods.getGoodsQty()-carts.get(i).getGoodsQty()>=0){
                    jishu++;
                }else {
                    //库存不足无法购买
                    return R.error().data("msg","无法购买,该商品库存不足,请联系工作人员添加");
                }
                //当循环完毕，相等表示都能够减库，再次循环，进行减库
                if (jishu==carts.size()){
                    for (int b=0;b<carts.size();b++){
                        //修改商品库存与销量
                        UpdateWrapper<Goods> goodsUp = new UpdateWrapper<>();
                        goodsUp.set("goods_qty",goods.getGoodsQty()-carts.get(b).getGoodsQty())
                                .set("sales",goods.getSales()+carts.get(b).getGoodsQty())
                                .eq("goods_id",goods.getGoodsId());
                        goodsMapper.update(null,goodsUp);
                        //修改订单状态已付款1
                        UpdateWrapper<Order> orderUp = new UpdateWrapper<>();
                        orderUp.set("state",1).eq("order_id",order.getOrderId());
                        orderMapper.update(null,orderUp);
                        cartMapper.deleteById(carts.get(i).getCartId());
                    }
                }
            }
            return R.ok().data("msg","下单成功");
        }else {
            //钱不够
            return R.error().data("msg","您的余额不足,无法购买");
        }
    }

    @ApiOperation("用户确认收货")
    @GetMapping("/upOrderState5")
    public R upOrderState5(@RequestParam("order_id") Integer order_id){
        UpdateWrapper<Order> wrapper = new UpdateWrapper<>();
        wrapper.set("state",5).eq("order_id",order_id);
        orderMapper.update(null, wrapper);
        return R.ok().data("msg","收货成功");
    }

//    订单状态
//    0 : 未付款
//    1 : 已付款
//    2 : 配送中
//    3 : 已完成
//    5 : 用户在已完成可进行第一次评论，追加评论
    @ApiOperation("管理员修改订单状态发货配送(2)")
    @GetMapping("/upOrderState2/{oid}")
    public R upOrderState2(@PathVariable("oid") Integer oid){
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("state",2).eq("order_id",oid);
        int update = orderMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","发货成功");
        }else {
            return R.error().data("msg","发货失败");
        }
    }

    @ApiOperation("管理员修改订单状态发货配送(3)")
    @GetMapping("/upOrderState3/{oid}")
    public R upOrderState3(@PathVariable("oid") Integer oid){
        UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("state",3).eq("order_id",oid);
        int update = orderMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","订单完成");
        }else {
            return R.error().data("msg","订单异常");
        }
    }
}
