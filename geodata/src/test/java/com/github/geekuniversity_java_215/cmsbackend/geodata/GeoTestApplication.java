package com.github.geekuniversity_java_215.cmsbackend.geodata;

import com.github.geekuniversity_java_215.cmsbackend.configuration.configurations.MultimoduleSpringBootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Tests need spring configuration
 */
@MultimoduleSpringBootApplication
// Legacy versions Intellij Idea support
@SpringBootApplication(scanBasePackages ="com.github.geekuniversity_java_215.cmsbackend")
public class GeoTestApplication {
}

