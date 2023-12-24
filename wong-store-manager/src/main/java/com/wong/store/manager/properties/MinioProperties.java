package com.wong.store.manager.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Jay Wong
 * @date 2023/12/23 22:41
 */
@Data
@ConfigurationProperties(prefix = "store.minio")
public class MinioProperties {
    private String endpointUrl;
    private String accessKey;
    private String secretKey;
    private String bucketName;
}
