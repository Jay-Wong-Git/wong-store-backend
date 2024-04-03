package com.wong.store.user.Service;

import com.wong.store.model.entity.user.UserAddress;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/30 20:56
 */
public interface UserAddressService {
    // 获取用户地址列表
    List<UserAddress> queryUserAddressList();

    // 根据id获取地址信息
    UserAddress queryById(Long id);
}
