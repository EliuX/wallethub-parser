package com.ef.config;

import com.ef.domain.persistence.MySQLRequestLogRepository;
import com.ef.domain.persistence.RequestLogRepository;
import com.ef.domain.service.RequestLogService;
import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

public class MySQLPersistModule extends AbstractModule {

    @Override
    protected void configure() {
        install(new JpaPersistModule("request-log"));

        bind(RequestLogRepository.class).to(MySQLRequestLogRepository.class);
    }
}
