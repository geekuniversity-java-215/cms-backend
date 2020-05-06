package com.github.geekuniversity_java_215.cmsbackend.payment.configurations;

import com.github.geekuniversity_java_215.cmsbackend.payment.services.PayPalService;
import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class PayPalAccount {

    @Value("${classpath:paypal.clientId}")
    private String clientId;

    @Value("${classpath:paypal.clientSecret}")
    private String clientSecret;

    @Value("${classpath:paypal.mode}")
    private String mode;

//    @Value("${server.port}")
//    private String port;

    
//
//
//    public static String CLIENT_ID;
//    public static String CLIENT_SECRET;
//    public static String MODE;
//    public static String SERVER_PORT;
//    public static String CONTEXT_PATH;
//
//    @Value("${paypal.clientId}")
//    public void setClientId(String clientId) {
//        CLIENT_ID = clientId;
//    }
//
//    @Value("${paypal.clientSecret}")
//    public void setClientSecret(String clientSecret) {
//        CLIENT_SECRET = clientSecret;
//    }
//
//    @Value("${paypal.mode}")
//    public void setMODE(String MODE) {
//        MODE = MODE;
//    }
//
//    @Value("${server.port}")
//    public void setServerPort(String port){SERVER_PORT=port;}

//    @Value("${server.servlet.context-path}")
//    public void setPath(String path){CONTEXT_PATH=path;}
}
