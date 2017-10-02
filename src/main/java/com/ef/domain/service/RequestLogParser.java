package com.ef.domain.service;

import com.ef.common.Utils;
import com.ef.domain.model.RequestLog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class RequestLogParser {

    public final List<RequestLog> parseRequestLogs(String inputText) {
        Scanner scanner = new Scanner(inputText).useDelimiter("\\s*\\|\\s*");

        List<RequestLog> requestLogs = new LinkedList<>();

        while(scanner.hasNext())
        {
            parseRequestLog(scanner.next())
                    .ifPresent(requestLogs::add);
        }

        return requestLogs;
    }

    protected Optional<RequestLog> parseRequestLog(String line) {
        final String[] tokens = line.split("\\s+");

        final Optional<RequestLog> requestLog = parseIp(tokens)
                .flatMap(ip -> parseDate(tokens).map(
                        date -> new RequestLog(
                                ip, date
                        )
                ));

        return requestLog;
    }

    private Optional<String> parseIp(final String[] tokens) {
        return Utils.parseIpv4Address(tokens[1]);
    }

    private Optional<Date> parseDate(String[] tokens) {
        return Utils.parseRequestLogDate(tokens[0]);
    }
}
