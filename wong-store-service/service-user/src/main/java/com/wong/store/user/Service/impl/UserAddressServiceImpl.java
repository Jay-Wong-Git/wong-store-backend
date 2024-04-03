package com.wong.store.user.Service.impl;

import com.wong.store.model.entity.user.UserAddress;
import com.wong.store.user.Service.UserAddressService;
import com.wong.store.user.mapper.UserAddressMapper;
import com.wong.store.utils.AuthContextUtil;
import jakarta.annotation.Resource;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/30 20:56
 */
@Service
public class UserAddressServiceImpl implements UserAddressService {
    @Resource
    private UserAddressMapper userAddressMapper;

    /**
     * 获取用户地址列表
     *
     * @return 用户地址列表
     */
    @Override
    public List<UserAddress> queryUserAddressList() {
        // 从ThreadLocal中获取用户id
        Long userId = AuthContextUtil.getUserInfo().getId();
        return userAddressMapper.queryUserAddressList(userId);
    }

    /**
     * 根据id获取地址信息
     *
     * @param id id
     * @return 用户地址对象
     */
    @Override
    public UserAddress queryById(Long id) {
        return userAddressMapper.queryById(id);
    }
}
