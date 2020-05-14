package com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.properties;

import com.github.geekuniversity_java_215.cmsbackend.core.data.constants.CorePropNames;
import lombok.AccessLevel;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.net.URL;

import static com.github.geekuniversity_java_215.cmsbackend.authserver.data.constants.AuthServerPropNames.*;
import static com.github.geekuniversity_java_215.cmsbackend.core.data.constants.CorePropNames.*;
import static com.pivovarit.function.ThrowingSupplier.unchecked;

@Configuration
@Getter
public class AuthServerConfig {

    private final Environment environment;

    @Value("${" + AUTHSERVER_CONFIRMATION_PATH + "}")
    @Getter(AccessLevel.NONE)
    private String confirmationPath;


    @Value("${" + AUTHSERVER_CONFIRMATION_REDIRECT_URL + "}")
    private String redirectUrl;


    private String confirmationUrl;



    @Autowired
    public AuthServerConfig(Environment environment) {
        this.environment = environment;
    }

    //FixMe вынести приседания с path в ядро.
    @PostConstruct
    private void postConstruct() {
        String protocol = environment.getProperty(SERVER_PROTOCOL);
        String host = environment.getProperty(SERVER_HOST);
        String portString = environment.getProperty(SERVER_PORT);
        Assert.notNull(protocol, "protocol == null");
        Assert.notNull(portString, "port == null");
        int port = Integer.parseInt(portString);
        String path = environment.getProperty(SERVER_SERVLET_CONTEXT_PATH);
        if (path!= null && path.length() > 0) {
            path = path.replaceAll("/$", "");
        }
        path +=  environment.getProperty(AUTHSERVER_CONFIRMATION_PATH);

        String finalPath = path;
        confirmationUrl = unchecked(() -> new URL(protocol, host, port, finalPath)).get().toString();
    }
}
