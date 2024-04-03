package com.wong.store.user.mapper;

import com.wong.store.model.entity.user.UserAddress;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/30 20:57
 */
@Mapper
public interface UserAddressMapper {
    // 根据用户Id获取用户地址列表
    List<UserAddress> queryUserAddressList(Long userId);

    // 根据id获取用户地址对象
    UserAddress queryById(Long id);
}
