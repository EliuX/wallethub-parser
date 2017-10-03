package com.ef.common;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class QueryArguments {

    private final Date startDate;

    private final Duration duration;

    private final Integer threshold;

    private Date endDate;

    public QueryArguments(
            Date startDate, Duration duration, Integer threshold
    ) {
        this.startDate = startDate;
        this.duration = duration;
        this.threshold = threshold;
    }

    public Date getStartDate() {
        return startDate;
    }

    public Duration getDuration() {
        return duration;
    }

    public Integer getThreshold() {
        return threshold;
    }

    public Date getEndDate(){
        if (endDate == null)
        {
            endDate = duration.addTo(getStartDate());
        }

        return endDate;
    }
}
