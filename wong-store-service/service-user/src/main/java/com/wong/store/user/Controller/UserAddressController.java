package com.wong.store.user.Controller;

import com.wong.store.model.entity.user.UserAddress;
import com.wong.store.model.vo.common.Result;
import com.wong.store.model.vo.common.ResultCodeEnum;
import com.wong.store.user.Service.UserAddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.annotation.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/30 20:55
 */
@Tag(name = "用户地址接口")
@RestController
@RequestMapping(value = "/api/user/userAddress")
public class UserAddressController {
    @Resource
    private UserAddressService userAddressService;

    /**
     * 获取用户地址列表接口
     *
     * @return 用户地址列表
     */
    @Operation(summary = "获取用户地址列表接口")
    @GetMapping("/auth/findUserAddressList")
    public Result<List<UserAddress>> queryUserAddressList() {
        List<UserAddress> list = userAddressService.queryUserAddressList();
        return Result.build(list, ResultCodeEnum.SUCCESS);
    }

    /**
     * 用于远程调用，根据id获取用户地址接口
     *
     * @param id 用户Id
     * @return 用户地址对象
     */

    @Operation(summary = "根据id获取地址信息接口")
    @GetMapping("getUserAddress/{id}")
    public UserAddress queryUserAddressById(@PathVariable Long id) {
        return userAddressService.queryById(id);
    }
}
