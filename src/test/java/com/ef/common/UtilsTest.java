package com.ef.common;

import org.junit.Assert;
import org.junit.Test;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Map;
import java.util.Optional;

import static org.junit.Assert.*;

public class UtilsTest {

    @Test
    public void parseArgsIntoMap() throws Exception {
        final Map<String, String> argsMap = Utils.parseArgsIntoMap(
                "--duration=hourly", "--threshold=100"
        );

        Assert.assertEquals(
                "The value of arg 'duration' was not successfully parsed",
                "hourly",
                argsMap.get("duration")
        );

        Assert.assertEquals(
                "The value of arg 'threshold' was not successfully parsed",
                "100",
                argsMap.get("threshold")
        );
    }

    @Test
    public void parseRequestLogDate() throws Exception {
        final Date validDate = new GregorianCalendar(
                2017, 0, 1,
                13, 10, 50
        ).getTime();


        Assert.assertEquals(
                "The valid representation of the date time was not properly parse",
                validDate,
                Utils.parseRequestLogDate("2017-01-01.13:10:50").get()
        );

        final Date secondValidDate = new GregorianCalendar(
                1985, 11, 31,
                00, 59, 00
        ).getTime();

        Assert.assertEquals(
                "The second valid representation of the date time was not properly parse",
                secondValidDate,
                Utils.parseRequestLogDate("1985-12-31.00:59:00").get()
        );

        Assert.assertFalse(
                "The invalid representation of the date time returned a valid value",
                Utils.parseRequestLogDate("1985-12-31:00:59:00").isPresent()
        );
    }

    @Test
    public void parseIpv4Address() throws Exception {
        Assert.assertEquals(
                "The valid IPv4 address was not properly parsed",
                "127.0.0.1",
                Utils.parseIpv4Address("127.0.0.1").get()
        );

        Assert.assertEquals(
                "The invalid IPv4 address returned a valid value",
                Optional.empty(),
                Utils.parseIpv4Address("256.11.0.2")
        );
    }
}