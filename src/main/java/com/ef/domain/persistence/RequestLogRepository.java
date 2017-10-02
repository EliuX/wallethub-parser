package com.ef.domain.persistence;

import com.ef.domain.model.RequestLog;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public interface RequestLogRepository {

    Collection<String> findByDateRangeAndThreshold(Date startDate, Date endDate, Integer threshold);

    boolean save(Collection<RequestLog> requestLogs);

    default boolean save(RequestLog... requestLogs) {
        return save(Arrays.asList(requestLogs));
    }
}
