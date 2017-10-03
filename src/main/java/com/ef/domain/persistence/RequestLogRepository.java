package com.ef.domain.persistence;

import com.ef.domain.model.IpCount;
import com.ef.domain.model.RequestLog;

import java.util.Arrays;
import java.util.Collection;
import java.util.Date;

public interface RequestLogRepository {

    Collection<IpCount> findByDateRangeAndThreshold(
            Date startDate, Date endDate, Integer threshold
    );

    void save(Collection<RequestLog> requestLogs);

    default void save(RequestLog... requestLogs) {
        save(Arrays.asList(requestLogs));
    }
}
