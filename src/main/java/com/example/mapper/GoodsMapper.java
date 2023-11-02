package com.example.mapper;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.example.entity.Goods;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author taozi
 * @since 2023-10-07
 */
@Repository
public interface GoodsMapper extends BaseMapper<Goods> {
 int countGoods();
}
