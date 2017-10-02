package com.ef;

import com.ef.common.CommandLineArguments;
import com.ef.common.Duration;
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

import java.util.Date;
import java.util.GregorianCalendar;

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

    @Test
    public void testParseArgs(){
        final CommandLineArguments arguments1 =
                Parser.parseArgs(
                        "Parser",
                        "--startDate=2017-01-01.13:00:00",
                        "--duration=hourly",
                        "--threshold=100"
                );

        Assert.assertEquals(
                "The duration argument was not properly parsed",
                Duration.HOURLY,
                arguments1.getDuration()
        );

        final Date startDate = new GregorianCalendar(
                2017, 0, 1,
                13, 00, 00
        ).getTime();

        Assert.assertEquals(
                "The date argument was not properly parsed",
                startDate,
                arguments1.getStartDate()
        );

        Assert.assertEquals(
                "The threshold argument was not properly parsed",
                100,
                arguments1.getThreshold().intValue()
        );
    }
}