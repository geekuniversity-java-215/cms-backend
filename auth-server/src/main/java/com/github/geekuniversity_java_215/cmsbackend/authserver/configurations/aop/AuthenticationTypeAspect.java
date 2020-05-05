package com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.aop;


import com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.AuthType;
import com.github.geekuniversity_java_215.cmsbackend.authserver.configurations.RequestScopeBean;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.springframework.util.Assert;


@Aspect
@Component
public class AuthenticationTypeAspect {

    // Contains current user auth type (obtained in spring security servlet chain filter)
    private final RequestScopeBean requestScopeBean;

    public AuthenticationTypeAspect(RequestScopeBean requestScopeBean) {
        this.requestScopeBean = requestScopeBean;
    }


    /**
     * Check auth type on method  - BasicAuth or Bearer
     * @param joinPoint
     */
    @Before("@annotation(ValidAuthenticationType)")
    public void validateAuthenticationType(JoinPoint joinPoint) {

        MethodSignature signature = (MethodSignature)joinPoint.getSignature();
        Method method = signature.getMethod();

        ValidAuthenticationType foundAnnotation = AnnotationUtils.findAnnotation(method, ValidAuthenticationType.class);

        Assert.notNull(foundAnnotation, "foundAnnotation == null");
        Set<AuthType> validAuthTypes = new HashSet<>(Arrays.asList(foundAnnotation.value()));
        AuthType currentAuthType = requestScopeBean.getAuthType();

        if (!validAuthTypes.contains(currentAuthType)) {
            throw new AccessDeniedException("Unauthenticated");
        }
    }

}


