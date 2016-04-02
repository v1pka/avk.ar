package ru.avk.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.AccessDecisionManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@Configuration
@EnableGlobalMethodSecurity(securedEnabled = true, proxyTargetClass = true)
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {

    @Autowired
    private VaadinAccessDecisionManager vaadinAccessDecisionManager;

    @Override
    protected AccessDecisionManager accessDecisionManager() {
        vaadinAccessDecisionManager.setAccessDecisionManager(super.accessDecisionManager());
        return vaadinAccessDecisionManager;
    }
}
