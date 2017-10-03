package com.ef.domain.model;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public final class IpCount {

    @Id
    private String ip;

    private Integer count;

    IpCount() {
    }

    public IpCount(String ip, Integer count) {
        this.ip = ip;
        this.count = count;
    }

    public String getIp() {
        return ip;
    }

    public Integer getCount() {
        return count;
    }
}
