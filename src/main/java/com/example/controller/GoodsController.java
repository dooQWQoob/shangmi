package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.entity.Goods;
import com.example.mapper.GoodsMapper;
import com.example.utils.R;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.stereotype.Controller;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.PushBuilder;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.Base64;
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
@RequestMapping("/goods")
public class GoodsController {
    @Autowired
    private GoodsMapper goodsMapper;

    @ApiOperation("添加商品")
    @PostMapping("/addGoods")
    public R addGoods(HttpServletRequest request, HttpServletResponse response, @RequestParam("goodsImage")MultipartFile file) throws IOException {
//        获取商品图片进行Base64编码
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] image = encoder.encode(file.getBytes());
//        String goodsName = request.getParameter("goodsName");
//        String goodsSpecs = request.getParameter("goodsSpecs");
//        Double goodsPrice = Double.valueOf(request.getParameter("goodsPrice"));
//        Integer goodsQty = Integer.valueOf(request.getParameter("goodsQty"));
//        String goodsClass = request.getParameter("goodsClass");
//        String goodsIntroduce = request.getParameter("goodsIntroduce");
        //获取请求数据进行包装
        Goods goods = new Goods();
        goods.setGoodsName(request.getParameter("goodsName"));
        goods.setGoodsImage(image);
        goods.setGoodsSpecs(request.getParameter("goodsSpecs"));
        goods.setGoodsPrice(Double.valueOf(request.getParameter("goodsPrice")));
        goods.setGoodsQty(Integer.valueOf(request.getParameter("goodsQty")));
        goods.setGoodsClass(request.getParameter("goodsClass"));
        goods.setGoodsIntroduce(request.getParameter("goodsIntroduce"));
//        System.out.println(goods);
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_name",goods.getGoodsName())
                    .eq("goods_specs",goods.getGoodsSpecs());
        Goods selectOne = goodsMapper.selectOne(queryWrapper);
        if (selectOne!=null){
            return R.error().data("msg","您已添加该商品,请不要重复添加");
        }else {
            goodsMapper.insert(goods);
            return R.ok().data("msg","添加成功");
        }
    }

    @ApiOperation("修改商品图片")
    @PutMapping("/upimage")
    public R upimage(@RequestParam("goodsImage") MultipartFile file,@RequestParam("goodsId") Integer goodsId) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] image = encoder.encode(file.getBytes());
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("goods_image",image).eq("goods_id",goodsId);
        int update = goodsMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }

    @ApiOperation("修改商品信息")
    @PostMapping("upGoods")
    public R upGoods(HttpServletRequest request, HttpServletResponse response, @RequestParam("goodsImage")MultipartFile file) throws IOException {
        Base64.Encoder encoder = Base64.getEncoder();
        byte[] image = encoder.encode(file.getBytes());
        Goods goods = new Goods();
        goods.setGoodsId(Integer.valueOf(request.getParameter("goodsId")));
        goods.setGoodsName(request.getParameter("goodsName"));
        goods.setGoodsImage(image);
        goods.setGoodsSpecs(request.getParameter("goodsSpecs"));
        goods.setGoodsPrice(Double.valueOf(request.getParameter("goodsPrice")));
        goods.setGoodsQty(Integer.valueOf(request.getParameter("goodsQty")));
        goods.setGoodsClass(request.getParameter("goodsClass"));
        goods.setGoodsIntroduce(request.getParameter("goodsIntroduce"));
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("goods_name",goods.getGoodsName())
                .set("goods_image",goods.getGoodsImage())
                .set("goods_specs",goods.getGoodsSpecs())
                .set("goods_price",goods.getGoodsPrice())
                .set("goods_qty",goods.getGoodsQty())
                .set("goods_class",goods.getGoodsClass())
                .set("goods_introduce",goods.getGoodsIntroduce())
                .eq("goods_id",goods.getGoodsId());
        int update = goodsMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败,请核对后修改");
        }
    }

    @ApiOperation("查询所有商品")
    @GetMapping("/selectAllGoods")
    public R selectAllGoods(@RequestParam Integer current){
        //设置分页参数 1：第几页 2：每页几条数据
        Page<Goods> page = new Page<>(current,5);
        //进行查询
        goodsMapper.selectPage(page, null);
        //将查询数据用实体类包装
        List<Goods> goods = page.getRecords();
        //获取商品总数
        int pages = goodsMapper.countGoods();
        //解码图片
        Base64.Decoder decoder = Base64.getDecoder();
        //遍历商品，把有图片的商品解码
        goods.forEach((g)->{
            if (g.getGoodsImage()!=null){
                g.setGoodsImage(decoder.decode(g.getGoodsImage()));
            }
        });
        return R.ok().data("goods",goods).data("pages",pages);
    }

    @ApiOperation("加入今日推荐")
    @GetMapping("/addRecommended/{goodsid}")
    public R addRecommended(@PathVariable("goodsid") Integer goodsid){
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("recommended",1).eq("goods_id",goodsid);
        int update = goodsMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","加入今日推荐成功");
        }else {
            return R.error().data("msg","加入今日推荐失败");
        }
    }

    @ApiOperation("移出今日推荐")
    @GetMapping("/outRecommended/{goodsid}")
    public R outRecommended(@PathVariable("goodsid") Integer goodsid){
        UpdateWrapper<Goods> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("recommended",0).eq("goods_id",goodsid);
        int update = goodsMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","移除今日推荐成功");
        }else {
            return R.error().data("msg","移除今日推荐失败");
        }
    }

    @ApiOperation("查询今日推荐商品")
    @GetMapping("/selectGoodsByRecommended")
    public R selectGoodsByRecommended(){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("recommended",1);
        List<Goods> goods = goodsMapper.selectList(queryWrapper);
        Base64.Decoder decoder = Base64.getDecoder();
        //遍历商品，把有图片的商品解码
        goods.forEach((g)->{
            if (g.getGoodsImage()!=null){
                g.setGoodsImage(decoder.decode(g.getGoodsImage()));
            }
        });
        return R.ok().data("goods",goods);
    }

    @ApiOperation("根据商品id查询商品")
    @GetMapping("/selectByGoodsid/{goodsid}")
    public R selectByGoodsid(@PathVariable("goodsid") Integer goodsid){
        Goods goods = goodsMapper.selectById(goodsid);
        Base64.Decoder decoder = Base64.getDecoder();
        goods.setGoodsImage(decoder.decode(goods.getGoodsImage()));
        return R.ok().data("goods",goods);
    }

    @ApiOperation("根据id删除商品")
    @GetMapping("/removeGoods/{goodsid}")
    public R removeGoods(@PathVariable("goodsid") Integer goodsid){
        int i = goodsMapper.deleteById(goodsid);
        if (i>0){
            return R.ok().data("msg","删除成功");
        }else {
            return R.error().data("msg","删除失败");
        }
    }

    @ApiOperation("模糊查询商品")
    @GetMapping("/selectGoodsByName/{goodsName}")
    public R selectGoodsByName(@PathVariable String goodsName){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("goods_name",goodsName);
        List<Goods> goods = goodsMapper.selectList(queryWrapper);
        Base64.Decoder decoder = Base64.getDecoder();
        //遍历商品，把有图片的商品解码
        goods.forEach((g)->{
            if (g.getGoodsImage()!=null){
                g.setGoodsImage(decoder.decode(g.getGoodsImage()));
            }
        });
        return R.ok().data("goods",goods);
    }

    @ApiOperation("根据价格查询小到大")
    @GetMapping("/selectGoodsByPrice")
    public R selectGoodsByPrice(@RequestParam("min")Integer min,@RequestParam("max") Integer max){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("goods_price",max)
                    .ge("goods_price",min)
                    .orderByAsc("goods_price");
        List<Goods> goods = goodsMapper.selectList(queryWrapper);
        Base64.Decoder decoder = Base64.getDecoder();
        //遍历商品，把有图片的商品解码
        goods.forEach((g)->{
            if (g.getGoodsImage()!=null){
                g.setGoodsImage(decoder.decode(g.getGoodsImage()));
            }
        });
        return R.ok().data("goods",goods);
    }

    @ApiOperation("根据价格查询大到小")
    @GetMapping("/selectGoodsByPriceDesc")
    public R selectGoodsByPriceDesc(@RequestParam("min")Integer min,@RequestParam("max") Integer max){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.le("goods_price",max)
                .ge("goods_price",min)
                .orderByDesc("goods_price");
        List<Goods> goods = goodsMapper.selectList(queryWrapper);
        Base64.Decoder decoder = Base64.getDecoder();
        //遍历商品，把有图片的商品解码
        goods.forEach((g)->{
            if (g.getGoodsImage()!=null){
                g.setGoodsImage(decoder.decode(g.getGoodsImage()));
            }
        });
        return R.ok().data("goods",goods);
    }

    @ApiOperation("根据商品类别查询商品")
    @GetMapping("/selectByType/{typeName}")
    public R selectByType(@PathVariable("typeName") String typeName){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_class",typeName);
        List<Goods> goods = goodsMapper.selectList(queryWrapper);
        Base64.Decoder decoder = Base64.getDecoder();
        //遍历商品，把有图片的商品解码
        goods.forEach((g)->{
            if (g.getGoodsImage()!=null){
                g.setGoodsImage(decoder.decode(g.getGoodsImage()));
            }
        });
        return R.ok().data("goods",goods);
    }

    @ApiOperation("今日热销（销量大于100）")
    @GetMapping("/selectBySales")
    public R selectBySales(){
        QueryWrapper<Goods> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("sales",100).orderByDesc("sales");
        List<Goods> goods = goodsMapper.selectList(queryWrapper);
        Base64.Decoder decoder = Base64.getDecoder();
        //遍历商品，把有图片的商品解码
        goods.forEach((g)->{
            if (g.getGoodsImage()!=null){
                g.setGoodsImage(decoder.decode(g.getGoodsImage()));
            }
        });
        return R.ok().data("goods",goods);
    }
}
