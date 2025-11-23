package src.main.java.com.sanrawat.parallelsynchronizers;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CyclicBarrier;

/**
 * =================== PARALLEL FILE READER USING SYNCHRONIZERS ===================

 * This class demonstrates **CountDownLatch** + **CyclicBarrier** in a real-world,
 * production-level parallel file-reading workflow.

 * ✔ CountDownLatch → Used when threads must finish BEFORE main thread continues
 *                    e.g., "Wait until all files are read".

 * ✔ CyclicBarrier → Used when threads must reach a common point BEFORE continuing
 *                    e.g., "All readers must be ready before reading starts".

 * ✔ Classpath-safe resource loading (works inside IntelliJ, Maven, Gradle, JAR)

 * When to use these?
 * ----------------------------------------------------------------------
 * ➤ Use CountDownLatch when main thread needs to WAIT for worker threads.
 * ➤ Use CyclicBarrier when worker threads need to WAIT for each other.
 * ➤ Use both when you want controlled parallelism with synchronization points.

 * This example loads:
 *   file1.txt, file2.txt, file3.txt

 * All stored in: src/main/resources/
 * IntelliJ copies them automatically to:
 * out/production/module/resources/
 */
public class ParallelFileReaderSynchronizers {

    public static void main(String[] args) throws Exception {


        String[] files = {"file1.txt", "file2.txt", "file3.txt"};

        // Barrier ensures all threads start reading together
        CyclicBarrier startBarrier = new CyclicBarrier(
                files.length,
                () -> System.out.println("\n[Barrier] All threads reached start → Begin reading!\n")
        );

        // Latch ensures main thread waits until ALL threads finish reading
        CountDownLatch doneLatch = new CountDownLatch(files.length);

        System.out.println("Starting parallel file reading...");

        for (String file : files) {
            new Thread(() -> readFile(file, startBarrier, doneLatch)).start();
        }

        // Wait for all reading threads to finish
        doneLatch.await();

        System.out.println("\n[Main] All files processed successfully.");
    }

    /**
     * Reads a file from classpath, waits at barrier, then signals latch when done.
     */
    private static void readFile(String fileName,
                                 CyclicBarrier barrier,
                                 CountDownLatch latch) {

        try {
            System.out.println(Thread.currentThread().getName() + " is ready to read " + fileName);

            // Wait until ALL threads are ready
            barrier.await();

            // Read file using classpath loader (correct for multi-module projects)
            String content = readFromResources(fileName);

            System.out.println(Thread.currentThread().getName() +
                    " → " + fileName + " content: " + content);

        } catch (Exception e) {
            System.err.println("ERROR reading " + fileName + ": " + e.getMessage());
        } finally {
            // Signal that this thread is DONE
            latch.countDown();
        }
    }

    /**
     * Loads a file from classpath using the correct classloader-safe method.

     * This works correctly because:
     * - IntelliJ marks src/main/resources as Resources Root
     * - Files are copied to classpath during build
     * - getResourceAsStream() works universally (JAR, Maven, Gradle, IntelliJ)
     */
    private static String readFromResources(String fileName)  {
        // StringBuilder used to collect file content
        StringBuilder builder = new StringBuilder();

        // Load file from classpath (src/main/resources)
        // IMPORTANT: This expects ONLY the file name, e.g. "file1.txt"
        try (InputStream is = ParallelFileReaderSynchronizers.class.getClassLoader().getResourceAsStream(fileName)) {
            // returns null if file isn't in classpath
            if (is == null) {
                builder.append("ERROR: File NOT found in classpath!"); // You see this because fileName is wrong or resource path is wrong
            } else {
                // Wrap InputStream so we can read text lines
                try (BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
                    String line;
                    // Read file line-by-line
                    while ((line = br.readLine()) != null) {
                        // This block executes only for unexpected exceptions
                        builder.append(line).append(" ");  // Append each line to output
                    }
                }
            }

        } catch (Exception e) {
            builder.append("ERROR: Could not read ").append(fileName);
        }


        return builder.toString().trim();
    }
}
