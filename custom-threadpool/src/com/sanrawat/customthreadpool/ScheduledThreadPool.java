package com.sanrawat.customthreadpool;
/**
 * SCHEDULED THREAD POOL DEMO
 * ---------------------------
 * - Used for periodic tasks (cron-like execution).
 * - Example: token refresh, cleanup jobs, health checks.
 */

import java.util.concurrent.*;

public class ScheduledThreadPool {

    public static void main(String[] args) {
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        scheduler.scheduleAtFixedRate(
                () -> System.out.println("Heartbeat: " + System.currentTimeMillis()),
                1, 2, TimeUnit.SECONDS
        );
    }
}
