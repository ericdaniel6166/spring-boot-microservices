package com.example.springbootmicroservicesframework.config.audit;

import com.example.springbootmicroservicesframework.utils.AppSecurityUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    private static final String APPLICATION_NAME_DEFAULT = "SYSTEM";

    @Value("${spring.application.name}")
    private String applicationName;

    @Override
    public Optional<String> getCurrentAuditor() {
        if (StringUtils.isBlank(applicationName)) {
            applicationName = APPLICATION_NAME_DEFAULT;
        }
        String currentAuditor;
        String currentUsername = AppSecurityUtils.getUsername();
        if (StringUtils.isNotBlank(currentUsername)) {
            currentAuditor = currentUsername;
        } else {
            currentAuditor = applicationName;
        }
        return Optional.of(currentAuditor);
    }
}
