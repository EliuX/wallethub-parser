package com.ef.domain.service;

import com.ef.common.ParserException;
import com.ef.common.Utils;
import com.ef.domain.model.RequestLog;
import com.sun.org.apache.regexp.internal.RE;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public final class RequestLogParser {

    private final Scanner scanner;

    public RequestLogParser(String text){
        this.scanner = new Scanner(text);
    }

    public RequestLogParser(File logFile){
        try {
            scanner = new Scanner(logFile, "UTF-8");
        }catch (FileNotFoundException ex){
            throw new ParserException(String.format(
                    "The log file '%s' was not found",
                    logFile
            ));
        }
    }

    public final List<RequestLog> parseRequestLogs() {
        scanner.useDelimiter("\\s*\\|\\s*");

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
