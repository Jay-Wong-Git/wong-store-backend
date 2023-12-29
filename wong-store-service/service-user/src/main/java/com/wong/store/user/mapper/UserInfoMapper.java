package com.wong.store.user.mapper;

import com.wong.store.model.entity.user.UserInfo;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Jay Wong
 * @date 2023/12/28 22:31
 */
@Mapper
public interface UserInfoMapper {
    // 根据用户名查询
    UserInfo queryByUsername(String username);

    // 添加用户
    void save(UserInfo userInfo);
}
