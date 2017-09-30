package com.ef.domain.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import java.util.Date;

@Entity
public final class RequestLog {

    @Id
    @GeneratedValue
    private Long id;

    private String ip;

    private Date date;

    private RequestLog() { }

    public RequestLog(String ip, Date date) {
        this.ip = ip;
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
