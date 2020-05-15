package com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.properties;

import com.github.geekuniversity_java_215.cmsbackend.core.utils.EnvStringBuilder;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.net.URL;

import static com.github.geekuniversity_java_215.cmsbackend.authserver.data.constants.AuthServerPropNames.*;
import static com.github.geekuniversity_java_215.cmsbackend.core.data.constants.CorePropNames.*;
import static com.pivovarit.function.ThrowingSupplier.unchecked;

@Configuration
@Getter
public class AuthServerConfig {

    private final EnvStringBuilder envStringbuilder;

    @Value("${" + AUTHSERVER_CONFIRMATION_PATH + "}")
    @Getter(AccessLevel.NONE)
    private String confirmationPath;


    @Value("${" + AUTHSERVER_CONFIRMATION_REDIRECT_URL + "}")
    private String redirectUrl;


    private String confirmationUrl;



    @Autowired
    public AuthServerConfig(EnvStringBuilder envStringbuilder) {
        this.envStringbuilder = envStringbuilder;
    }

    @PostConstruct
    private void postConstruct() {
        confirmationUrl  =
            envStringbuilder.buildURL(envStringbuilder.getProperty(AUTHSERVER_CONFIRMATION_PATH));
    }
}
