package com.example.springbootmicroservicesframework.config.audit;

import com.example.springbootmicroservicesframework.utils.AppSecurityUtils;
import org.springframework.data.domain.AuditorAware;

import java.util.Optional;

public class AuditorAwareImpl implements AuditorAware<String> {

    @Override
    public Optional<String> getCurrentAuditor() {
        return Optional.of(AppSecurityUtils.getCurrentAuditor());
    }
}
