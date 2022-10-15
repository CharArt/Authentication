package com.converter.converter.auth.tools;

import java.util.Date;

public enum TimeExpiration {
    FIFTEEN_SECOND(new Date(15 * 1000).getTime()),
    THIRTY_SECOND(new Date(30 * 1000).getTime()),
    ONE_MINUT(new Date(60 * 1000).getTime()),
    FIFTEEN_MINUTES(new Date(15 * 60 * 1000).getTime()),
    THIRTY_MINUTES(new Date(30 * 60 * 1000).getTime()),
    FORTY_FIVE_MINUTES(new Date(45 * 60 * 1000).getTime()),
    ONE_HOUR(new Date(60 * 60 * 1000).getTime()),
    TWO_HOURS(new Date(2 * 60 * 60 * 1000).getTime()),
    FORE_HOURS(new Date(4 * 60 * 60 * 1000).getTime()),
    EIGHT_HOURS(new Date(8 * 60 * 60 * 1000).getTime()),
    TWELVE_HOURS(new Date(12 * 60 * 60 * 1000).getTime());
    private Long time;

    TimeExpiration(long time) {
        this.time = time;
    }

    public long getTime() {
        return time;
    }
}
