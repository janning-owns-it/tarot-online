package com.janning_owns_it.tarot.helper;

import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

public class RedisTTLHelper {

    private static final ZoneId IRELAND_ZONE = ZoneId.of("Europe/Dublin");

    public static long getSecondsUntilMidnight() {
        ZonedDateTime now = ZonedDateTime.now(IRELAND_ZONE);
        return Duration.between(now, now.toLocalDate().plusDays(1)
                .atStartOfDay(IRELAND_ZONE)).getSeconds();
    }
}