
package CoreLogic;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class Main {

    public static void main(String[] args) {

        try {

            long startTime = System.currentTimeMillis();

            File directory = new File("../Files");

            File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

            if (files == null || files.length == 0) {
                System.out.println("No files found.");
                return;
            }

            int maxFiles = Math.min(files.length, 100);

            System.out.println("Processing files using 4 threads...\n");

            ExecutorService executor = Executors.newFixedThreadPool(4);

            List<Future<FileResult>> futures = new ArrayList<>();

            for (int i = 0; i < maxFiles; i++) {

                FileTask task = new FileTask(files[i]);

                Future<FileResult> future = executor.submit(task);

                futures.add(future);
            }

            int totalLines = 0;
            int totalWords = 0;

            for (Future<FileResult> future : futures) {

                try {

                    FileResult result = future.get();

                    System.out.println("File: " + result.getFileName());
                    System.out.println("Lines: " + result.getLineCount());
                    System.out.println("Words: " + result.getWordCount());
                    System.out.println();

                    totalLines += result.getLineCount();
                    totalWords += result.getWordCount();

                } catch (InterruptedException | ExecutionException e) {
                    System.out.println("Error while processing a file: " + e.getMessage());
                }
            }

            executor.shutdown();

            System.out.println("----------------------------------");
            System.out.println("Summary");
            System.out.println("----------------------------------");
            System.out.println("Total Files Processed: " + maxFiles);
            System.out.println("Total Lines: " + totalLines);
            System.out.println("Total Words: " + totalWords);

            long endTime = System.currentTimeMillis();

            System.out.println("\nTotal Execution Time: " + (endTime - startTime) + " ms");

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }
}