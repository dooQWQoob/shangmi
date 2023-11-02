package com.example.service.impl;

import com.example.entity.Cart;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.CartMapper;
import com.example.service.ICartService;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author taozi
 * @since 2023-10-07
 */
@Service
public class CartServiceImpl extends ServiceImpl<CartMapper, Cart> implements ICartService {

}
