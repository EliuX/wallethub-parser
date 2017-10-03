package com.ef.config;

import com.ef.domain.persistence.MySQLRequestLogRepository;
import com.ef.domain.persistence.RequestLogRepository;
import com.ef.domain.service.RequestLogService;
import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Singleton;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;

public class ParserModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(RequestLogService.class);
    }
}
