package com.ef.common;

import java.util.Calendar;
import java.util.Date;
import java.util.Optional;

public enum Duration {

    HOURLY("hourly", 1, Calendar.HOUR), DAILY("daily", 1, Calendar.DATE);

    private final String paramName;

    private final int offset;

    private final int calendarField;

    Duration(String paramName, int offset, int calendarField) {
        this.paramName = paramName;
        this.offset = offset;
        this.calendarField = calendarField;
    }

    public String paramName() {
        return paramName;
    }

    public static Optional<Duration> parseFromParamName(String paramName) {
        try {
            return Optional.of(valueOf(paramName.toUpperCase()));
        } catch (IllegalArgumentException | NullPointerException ex) {
            return Optional.empty();
        }
    }

    public Date addTo(Date originalDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(originalDate);

        cal.add(calendarField, offset);

        return cal.getTime();
    }
}
