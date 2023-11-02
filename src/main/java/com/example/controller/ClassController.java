package com.example.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Class;
import com.example.mapper.ClassMapper;
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
@RequestMapping("/class")
public class ClassController {
    @Autowired
    private ClassMapper classMapper;

    @ApiOperation("查询所有商品类别")
    @GetMapping("/allClass")
    public R allClass(){
        List<Class> classes = classMapper.selectList(null);
        return R.ok().data("clas",classes);
    }

    @ApiOperation("添加新的商品类")
    @PostMapping(value = "/addClass",produces = {"application/json;charset=UTF-8;"})
    public R addClass(@RequestBody Class c){
        QueryWrapper<Class> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("goods_class",c.getGoodsClass());
        Class aClass = classMapper.selectOne(queryWrapper);
        if (aClass!=null){
            return R.error().data("msg","该类型您已添加");
        }else {
            classMapper.insert(c);
            return R.ok().data("msg","添加成功");
        }
    }

    @ApiOperation("修改商品类别")
    @PostMapping(value = "/upClass",produces = {"application/json;charset=UTF-8;"})
    public R upClass(@RequestBody Class c){
        UpdateWrapper<Class> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("goods_class",c.getGoodsClass()).eq("class_id",c.getClassId());
        int update = classMapper.update(null, updateWrapper);
        if (update>0){
            return R.ok().data("msg","修改成功");
        }else {
            return R.error().data("msg","修改失败");
        }
    }

    @ApiOperation("删除类别")
    @GetMapping("/deleteById/{id}")
    public R deleteById(@PathVariable("id") Integer id){
        int i = classMapper.deleteById(id);
        if (i>0){
            return R.ok().data("msg","删除成功");
        }else {
            return R.error().data("msg","删除失败");
        }
    }
}
