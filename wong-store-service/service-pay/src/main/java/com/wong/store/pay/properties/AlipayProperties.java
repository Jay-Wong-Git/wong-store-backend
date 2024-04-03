package com.wong.store.pay.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Jay Wong
 * @date 2023/12/31 21:16
 */

@Data
@ConfigurationProperties(prefix = "store.alipay")
public class AlipayProperties {
    private String alipayUrl;
    private String appPrivateKey;
    public String alipayPublicKey;
    private String appId;
    public String returnPaymentUrl;
    public String notifyPaymentUrl;

    public final static String FORMAT = "json";
    public final static String CHARSET = "utf-8";
    public final static String SIGN_TYPE = "RSA2";
}
