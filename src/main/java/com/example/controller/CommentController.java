package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Comment;
import com.example.entity.Order;
import com.example.mapper.CommentMapper;
import com.example.mapper.OrderMapper;
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
@RequestMapping("/comment")
public class CommentController {

    @Autowired
    private CommentMapper commentMapper;

    @Autowired
    private OrderMapper orderMapper;

    @ApiOperation("根据用户id及商品id+订单id(该订单必须已完成)添加评论")
    @PostMapping(value = "/addComment",produces = {"application/json;charset=UTF-8;"})
    public R addComment(@RequestBody Comment comment){
        //一个订单一个用户可评论两次
        //先统计该用户对该订单的评论数量
        int i = commentMapper.countComment(comment.getuId(),comment.getOrderId());
        if (i==0){
            //表示该订单对应的该用户未评论
            commentMapper.insert(comment);
            //第一次评论后，将订单状态修改为5，表示可追加评论
            UpdateWrapper<Order> updateWrapper = new UpdateWrapper<>();
            updateWrapper.set("state",5).eq("order_id",comment.getOrderId());
            orderMapper.update(null,updateWrapper);
            return R.ok().data("msg","评论成功,感谢您的购买");
        }else if (i==1){
            //表示已评论过，可追加评论
            commentMapper.insert(comment);
            return R.ok().data("msg","追加评论成功");
        }else if (i>1){
            //表示该用户已评论两次，不允许再次评论
            return R.error().data("msg","您对该订单的评论量已超标(达两次)");
        }else {
            return R.error().data("msg","评论失败,请联系管理员");
        }
    }

    @ApiOperation("根据评论id删除")
    @GetMapping("/deleteById/{cid}")
    public R deleteById(@PathVariable("cid") Integer cid){
        int i = commentMapper.deleteById(cid);
        if (i>0){
            return R.ok().data("msg","删除成功");
        }else {
            return R.error().data("msg","删除失败");
        }
    }

    @ApiOperation("根据用户id查询评论")
    @GetMapping("/selectByUid/{uid}")
    public R selectByUid(@PathVariable("uid") Integer uid){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("u_id",uid);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return R.ok().data("msg",comments);
    }

    @ApiOperation("根据商品id查询评论")
    @GetMapping("/selectByGoodsId/{goodsid}")
    public R selectByGoodsId(@PathVariable("goodsid") Integer goodsid){
        QueryWrapper<Comment> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("goods_ids",goodsid);
        List<Comment> comments = commentMapper.selectList(queryWrapper);
        return R.ok().data("comments",comments);
    }

}
