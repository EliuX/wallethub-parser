package com.ef.domain.persistence;

import com.ef.common.ParserException;
import com.ef.domain.model.IpCount;
import com.ef.domain.model.RequestLog;

import javax.inject.Inject;
import javax.persistence.*;
import java.util.Collection;
import java.util.Date;
import java.util.List;

public class MySQLRequestLogRepository implements RequestLogRepository {

    private EntityManager entityManager;

    @Inject
    public MySQLRequestLogRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public Collection<IpCount> findByDateRangeAndThreshold(
            Date startDate, Date endDate, Integer threshold
    ) {
        StringBuilder sb = new StringBuilder("SELECT DISTINCT ip, COUNT(id) as count ");
        sb.append("FROM requestlog WHERE ");
        sb.append("date BETWEEN ? and ? GROUP BY ip ");
        sb.append("HAVING COUNT(ip) >= ?");

        final Query query = entityManager.createNativeQuery(sb.toString(), IpCount.class);
        query.setParameter(1, startDate);
        query.setParameter(2, endDate);
        query.setParameter(3, threshold);

        final List<IpCount> resultList = query.getResultList();

        return resultList;
    }

    @Override
    public void save(Collection<RequestLog> requestLogs) {
        final EntityTransaction transaction = entityManager.getTransaction();

        try {
            transaction.begin();

            requestLogs.forEach(this::insert);

            transaction.commit();
        } catch (Exception ex) {
            transaction.rollback();

            throw new ParserException("Error while inserting the new request logs");
        }
    }

    public void insert(RequestLog requestLog) {
        Query query = entityManager.createNativeQuery(
                "INSERT INTO requestlog (ip, date) VALUES(?,?)"
        );

        query.setParameter(1, requestLog.getIp());
        query.setParameter(2, requestLog.getDate());

        query.executeUpdate();
    }
}
