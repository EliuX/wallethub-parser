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