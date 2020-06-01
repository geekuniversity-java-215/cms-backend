package com.github.geekuniversity_java_215.cmsbackend.payment.configurations;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class PayPalAccount {

    @Value("${paypal.clientId}")
    private String clientId;

    @Value("${paypal.clientSecret}")
    private String clientSecret;

    @Value("${paypal.mode}")
    private String mode;

    @Value("${paypal.username}")
    private String username;

    @Value("${paypal.password}")
    private String password;

    @Value("${paypal.signature}")
    private String signature;


}
