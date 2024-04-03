package com.wong.store.feign.user;

import com.wong.store.model.entity.user.UserAddress;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author Jay Wong
 * @date 2023/12/30 22:50
 */
@FeignClient("service-user")
public interface UserFeignClient {
    @GetMapping("/api/user/userAddress/getUserAddress/{id}")
    public abstract UserAddress queryUserAddressById(@PathVariable Long id);
}
