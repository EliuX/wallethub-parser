package com.ef.domain.service;

import com.ef.domain.model.RequestLog;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class RequestLogService {

    private EntityManager entityManager;

    @Inject
    public RequestLogService(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Collection<String> findIps(Date startDate, Date endDate, Integer threshold) {
        return Collections.emptyList();
    }
}
