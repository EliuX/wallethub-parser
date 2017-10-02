package com.ef.domain.persistence;

import com.ef.domain.model.RequestLog;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Date;

public class MySQLRequestLogRepository implements RequestLogRepository{

    private EntityManager entityManager;

    @Inject
    public MySQLRequestLogRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Collection<String> findByDateRangeAndThreshold(
            Date startDate, Date endDate, Integer threshold
    ) {


        return null;
    }

    @Override
    public boolean save(Collection<RequestLog> requestLogs) {


        return false;
    }
}
