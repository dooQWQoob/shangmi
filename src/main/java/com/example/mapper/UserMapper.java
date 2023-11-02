package com.example.mapper;

import com.example.entity.User;
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
public interface UserMapper extends BaseMapper<User> {
    Integer recoveryUser(String userPhone,String userPwd);

    Integer removeUserById(Integer uid);
}
