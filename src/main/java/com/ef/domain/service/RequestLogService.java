package com.ef.domain.service;

import com.ef.common.QueryArguments;
import com.ef.domain.model.IpCount;
import com.ef.domain.model.RequestLog;
import com.ef.domain.persistence.MySQLRequestLogRepository;
import com.ef.domain.persistence.RequestLogRepository;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.io.File;
import java.util.Collection;
import java.util.List;

@Singleton
public class RequestLogService {

    private final RequestLogRepository requestLogRepository;

    @Inject
    public RequestLogService(MySQLRequestLogRepository requestLogRepository) {
        this.requestLogRepository = requestLogRepository;
    }

    public void parseLogFileIntoDatabase(File file) {
        final List<RequestLog> requestLogs = new RequestLogParser(file)
                .parseRequestLogs();

        requestLogRepository.save(requestLogs);

        requestLogs.forEach(System.out::println);
    }

    public void printRequestLogs(QueryArguments queryArguments) {
        System.out.println(String.format(
                "Ips from %s to %s with more than %d requests",
                queryArguments.getStartDate(),
                queryArguments.getEndDate(),
                queryArguments.getThreshold()
        ));
        System.out.println("=========================================");

        final Collection<IpCount> result =
                requestLogRepository.findByDateRangeAndThreshold(
                        queryArguments.getStartDate(),
                        queryArguments.getEndDate(),
                        queryArguments.getThreshold()
                );

        result.stream()
                .map(ipCount -> String.format(
                        "-%s: \t%d requests",
                        ipCount.getIp(),
                        ipCount.getCount())
                )
                .forEach(System.out::println);

        System.out.println("=========================================");
        System.out.printf(
                "  A total of %d ips where found%n",
                result.size()
        );
        System.out.println("=========================================");
    }
}
