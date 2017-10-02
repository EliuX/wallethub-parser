package com.ef.domain.service;

import com.ef.domain.model.RequestLog;
import com.ef.domain.persistence.RequestLogRepository;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;

public class RequestLogService {

    private RequestLogRepository requestLogRepository;


    public Collection<String> findIps(Date startDate, Date endDate, Integer threshold) {
        return Collections.emptyList();
    }

    public Collection<RequestLog> findRequestsByIp(String ip){
        return Collections.emptyList();
    }
}
