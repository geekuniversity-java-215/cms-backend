package com.github.geekuniversity_java_215.cmsbackend.core;

import com.github.geekuniversity_java_215.cmsbackend.core.configuration.MultimoduleSpringBootApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


/**
 * Tests need spring configuration
 */
@MultimoduleSpringBootApplication
// Intellij Idea scanning Spring Configuration fix
@SpringBootApplication(scanBasePackages ="com.github.geekuniversity_java_215.cmsbackend")
public class CoreTestApplication {
}
