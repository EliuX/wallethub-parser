package com.ef;

import com.ef.config.MySQLPersistModule;
import com.ef.config.ParserModule;
import com.ef.domain.service.RequestLogService;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.persist.PersistService;
import com.google.inject.persist.jpa.JpaPersistModule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public class ParserTest {

    private Injector injector;

    @Test
    public void testIoC() {
        injector = Guice.createInjector(
                new ParserModule()
        );

        Assert.assertNotNull(
                "The RequestLogService was not obtained",
                injector.getInstance(RequestLogService.class)
        );
    }
}