package com.wong.store.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;

/**
 * @author Jay Wong
 * @date 2023/12/22 21:28
 */
@Data
@ConfigurationProperties(prefix = "store.auth")
public class UserAuthProperties {
    private List<String> noAuthUrls;
}
