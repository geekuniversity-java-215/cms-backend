package com.github.geekuniversity_java_215.cmsbackend.core.utils;

import com.github.geekuniversity_java_215.cmsbackend.core.data.constants.CorePropNames;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.net.URL;

import static com.pivovarit.function.ThrowingSupplier.unchecked;

@Component
public class EnvStringBuilder {

    private final Environment environment;

    private String servletPath;
    private int port;
    private String host;
    private String protocol;

    public EnvStringBuilder(Environment environment) {
        this.environment = environment;
    }

    @PostConstruct
    private void postConstruct() {

        protocol = environment.getProperty(CorePropNames.SERVER_PROTOCOL);
        host = environment.getProperty(CorePropNames.SERVER_HOST);
        String portString = environment.getProperty(CorePropNames.SERVER_PORT);

        if (StringUtils.isBlank(protocol)) {
            protocol = "http";
        }
        if (StringUtils.isBlank(host)) {
            protocol = "localhost";
        }


        //Assert.notNull(protocol, "protocol == null");
        //Assert.notNull(host, "host == null");
        Assert.notNull(portString, "port == null");

        port = Integer.parseInt(portString);
        servletPath = environment.getProperty(CorePropNames.SERVER_SERVLET_CONTEXT_PATH);
        if (!StringUtils.isBlank(servletPath)) {
            servletPath = servletPath.replaceAll("/$", ""); // replace only last occurrence of /
        }
    }

    /**
     *
     * @param path should begin with '/'
     * @return
     */
    public String buildURL(String path) {
        String result;

        if (StringUtils.isBlank(path) || !path.startsWith("/")) {
            throw new IllegalArgumentException("path should begins with '/'");
        }
        URL url =  unchecked(() -> new URL(protocol, host, port, servletPath + path)).get();
        return url.toString();
    }

    public String getProperty(String key) {
        return environment.getProperty(key);
    }


}
