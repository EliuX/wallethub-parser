package com.ef.config;

import com.ef.domain.persistence.MySQLRequestLogRepository;
import com.ef.domain.persistence.RequestLogRepository;
import com.ef.domain.service.RequestLogService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

public class MySQLPersistModuleTest {

    private void initPersistService(Injector injector) {
        PersistService instance = injector.getInstance(PersistService.class);
        instance.start();
    }

    @Test
    public void testConfiguration() throws Exception {
        Injector injector = Guice.createInjector(
              new ParserModule(),
              new MySQLPersistModule()
        );

        initPersistService(injector);

        Assert.assertEquals(
                "The MySQLRequestLogRepository class was not injected",
                MySQLRequestLogRepository.class,
                injector.getInstance(RequestLogRepository.class).getClass()
        );
    }
}