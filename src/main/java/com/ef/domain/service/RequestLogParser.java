package com.ef.domain.service;

import com.ef.domain.model.RequestLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RequestLogParser {

    private static final String IPV4_PATTERN_STRING =
            "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
                    "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    static final Pattern IPV4_PATTERN = Pattern.compile(IPV4_PATTERN_STRING);

    static final SimpleDateFormat DATETIME_FORMATTER =
            new SimpleDateFormat("yyyy-MM-dd.HH:mm:ss");

    public final List<RequestLog> parseRequestLogs(String text) {
        return Arrays.asList(text.split("\\|")).stream()
                .map(String::trim)
                .map(this::parseRequestLog)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());
    }

    protected Optional<RequestLog> parseRequestLog(String line) {
        final String[] tokens = line.split(" ");

        final Optional<RequestLog> requestLog = parseIp(tokens)
                .flatMap(ip -> parseDate(tokens).map(
                        date -> new RequestLog(
                                ip, date
                        )
                ));

        return requestLog;
    }

    private Optional<String> parseIp(final String[] tokens) {
        if (IPV4_PATTERN.matcher(tokens[1]).matches()) {
            return Optional.of(tokens[1]);
        } else {
            return Optional.empty();
        }
    }

    private Optional<Date> parseDate(String[] tokens) {
        try {
            final Date parsedDate =
                    DATETIME_FORMATTER.parse(tokens[0]);

            return Optional.of(parsedDate);
        } catch (ParseException e) {
            return Optional.empty();
        }
    }
}
