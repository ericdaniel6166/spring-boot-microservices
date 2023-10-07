package com.example.springbootmicroservicesframework.config.audit;

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
//        String currentAuditor;
//        if (StringUtils.isNotBlank(CommonUtils.getCurrentUsername())) {
//            currentAuditor = CommonUtils.getCurrentUsername();
//        } else {
//            currentAuditor = applicationName;
//        }
        String currentAuditor = applicationName;
        return Optional.of(currentAuditor);
    }
}
