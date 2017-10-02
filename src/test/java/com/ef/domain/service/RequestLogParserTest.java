package com.ef.domain.service;

import com.ef.domain.model.RequestLog;
import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

import static com.ef.domain.service.RequestLogParser.*;

public class RequestLogParserTest {

    @Test
    public void testIpv4Pattern() throws Exception {
        Assert.assertTrue(
                "The IPv4 pattern did not matched the local address",
                IPV4_PATTERN.matcher("127.0.0.1").matches()
        );

        Assert.assertTrue(
                "The IPv4 pattern did not matched a regular network address",
                IPV4_PATTERN.matcher("192.168.0.1").matches()
        );
    }

    @Test
    public void testDateTimePattern() {
        final Date sampleDate = new GregorianCalendar(
                2017, 0, 1,
                13, 10, 50
        ).getTime();

        Assert.assertEquals(
                "The calendar was not properly converted into date",
                "2017-01-01.13:10:50",
                DATETIME_FORMATTER.format(sampleDate)
        );

        final Date expectedDate = new GregorianCalendar(
                1985, 11, 31,
                00, 59, 00
        ).getTime();

        try {
            Assert.assertEquals(
                    "The string representation of the date time was not properly parse",
                    expectedDate,
                    DATETIME_FORMATTER.parse("1985-12-31.00:59:00")
            );
        } catch (ParseException ex) {
            Assert.fail("Failed parsing the string representation of the date time");
        }
    }

    @Test
    public void testParseRequestLog() {
        RequestLogParser parser = new RequestLogParser();

        final Optional<RequestLog> parsedRequestLog =
                parser.parseRequestLog("1985-11-06.00:30:00 192.168.0.1");

        RequestLog expectedRequestLog = new RequestLog(
                "192.168.0.1",
                new GregorianCalendar(
                        1985, 10, 6,
                        00, 30, 00
                ).getTime()
        );

        Assert.assertEquals(
                "The RequestLog was not parsed successfully",
                expectedRequestLog,
                parsedRequestLog.get()
        );
    }

    @Test
    public void testParseRequestLogs(){
        RequestLogParser parser = new RequestLogParser();

        String textToParse = "1985-11-06.00:30:00 192.168.0.1|" +
                "not valid text | 1985-11-06 127.0.0.1|" +
                "1990-11-06.00:30:00 127.0.0.1";

        final List<RequestLog> requestLogs = parser.parseRequestLogs(textToParse);

        Assert.assertEquals(
                "The tokens were not properly parsed",
                2,
                requestLogs.size()
        );

        RequestLog expectedLastParsedLog = new RequestLog(
                "127.0.0.1",
                new GregorianCalendar(
                        1990, 10, 6,
                        00, 30, 00
                ).getTime()
        );
        Assert.assertEquals(
                "The second request log was not successfully parsed",
                expectedLastParsedLog,
                requestLogs.get(1)
        );
    }
}