package com.example.service.impl;

import com.example.entity.Comment;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.mapper.CommentMapper;
import com.example.service.ICommentService;
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
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements ICommentService {

}
