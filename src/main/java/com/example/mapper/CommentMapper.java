package com.example.mapper;

import com.example.entity.Comment;
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
public interface CommentMapper extends BaseMapper<Comment> {
    int countComment(Integer uid,Integer orderid);
}
