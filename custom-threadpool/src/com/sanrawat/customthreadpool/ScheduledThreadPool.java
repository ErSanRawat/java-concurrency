package com.sanrawat.customthreadpool;
/**
 * SCHEDULED THREAD POOL DEMO
 * ---------------------------
 * - Used for periodic tasks (cron-like execution).
 * - Example use cases:
 *      - Token refresh
 *      - Cache invalidation
 *      - Cleanup jobs
 *      - Health-check pings
 */

import java.util.concurrent.*;

public class ScheduledThreadPool {
    public static void main(String[] args) {

        // Create scheduler with 2 threads
        // Both threads can run periodic tasks concurrently
        ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

        // scheduleAtFixedRate parameters:
        //   1. Task to execute
        //   2. Initial delay (1 sec)
        //   3. Interval between executions (2 sec)
        //   4. Time unit
        scheduler.scheduleAtFixedRate(
                () -> {
                    // This task repeats every 2 seconds
                    System.out.println("Heartbeat at ms: " + System.currentTimeMillis());
                },
                1,      // start after 1 second
                2,      // repeat every 2 seconds
                TimeUnit.SECONDS
        );

        // NOTE:
        // We intentionally do NOT shutdown this scheduler
        // because it's meant to be long-running like real cron jobs.
    }
}
