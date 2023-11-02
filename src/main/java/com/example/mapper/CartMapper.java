package com.example.mapper;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

import com.example.entity.Cart;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author taozi
 * @since 2023-10-07
 */
@Repository
public interface CartMapper extends BaseMapper<Cart> {
    int removeCartById(Integer cartId);

    int countByUid(Integer uid);

    Cart selectByCartId(@RequestParam("cartId") Integer cartId);

}
