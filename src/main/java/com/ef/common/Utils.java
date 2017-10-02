package com.ef.common;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class Utils {

    public static final SimpleDateFormat DATETIME_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");

    private static final Pattern PARAM_PATTERN
            = Pattern.compile("^[-]{0,2}(\\w+)[=](.*)$");

    private static final String IPV4_PATTERN_STRING =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    private static final Pattern IPV4_PATTERN = Pattern.compile(IPV4_PATTERN_STRING);

    public static Optional<Date> parseRequestLogDate(String dateStr) {
        try {
            final Date parsedDate =
                    DATETIME_FORMATTER.parse(dateStr);

            return Optional.of(parsedDate);
        } catch (ParseException e) {
            return Optional.empty();
        }
    }

    public static Optional<String> parseIpv4Address(String ipStr)
    {
        if (IPV4_PATTERN.matcher(ipStr).matches()) {
            return Optional.of(ipStr);
        } else {
            return Optional.empty();
        }
    }

    public static final Map<String, String> parseArgsIntoMap(String... args) {
        return Arrays.asList(args).stream()
                .map(PARAM_PATTERN::matcher)
                .filter(Matcher::matches)
                .collect(Collectors.toMap(m -> m.group(1), m -> m.group(2)));
    }
}
