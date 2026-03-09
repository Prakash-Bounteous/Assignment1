//package CoreLogic;
//
//import java.io.File;
//import java.util.*;
//import java.util.concurrent.*;
//
//public class Main {
//
//    public static void main(String[] args) {
//
//        long startTime = System.currentTimeMillis();
//
//        try {
//
//            File directory = new File("../Files");
//
//            if (!directory.exists() || !directory.isDirectory()) {
//                System.out.println("Directory does not exist.");
//                return;
//            }
//
//            File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));
//
//            if (files == null || files.length == 0) {
//                System.out.println("No files found.");
//                return;
//            }
//
//            //sorting files based on last modified time
//            Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));
//
//            int maxFiles = Math.min(files.length, 100);
//
//            System.out.println("Started processing " + maxFiles + " files using 4 threads...\n");
//
//            ExecutorService executor = Executors.newFixedThreadPool(4);
//
//            List<Future<FileResult>> futures = new ArrayList<>();
//
//            for (int i = 0; i < maxFiles; i++) {
//
//                FileTask task = new FileTask(files[i]);
//
//                Future<FileResult> future = executor.submit(task);
//
//                futures.add(future);
//            }
//
//            int totalLines = 0;
//            int totalWords = 0;
//
//            for (Future<FileResult> future : futures) {
//
//                try {
//
//                    FileResult result = future.get();
//
//                    System.out.println("File: " + result.getFileName());
//                    System.out.println("Lines: " + result.getLineCount());
//                    System.out.println("Words: " + result.getWordCount());
//                    System.out.println();
//
//                    totalLines += result.getLineCount();
//                    totalWords += result.getWordCount();
//
//                } catch (InterruptedException | ExecutionException e) {
//
//                    System.out.println("Error processing a file: " + e.getMessage());
//                }
//            }
//
//            executor.shutdown();
//
//            System.out.println("----------------------------------");
//            System.out.println("Summary");
//            System.out.println("----------------------------------");
//            System.out.println("Total Files Processed: " + maxFiles);
//            System.out.println("Total Lines: " + totalLines);
//            System.out.println("Total Words: " + totalWords);
//
//        } catch (Exception e) {
//
//            System.out.println("Unexpected error: " + e.getMessage());
//        }
//
//        long endTime = System.currentTimeMillis();
//
//        System.out.println("\nTotal Execution Time: " + (endTime - startTime) + " ms");
//    }
//}

package CoreLogic;

import java.io.File;
import java.util.*;
import java.util.concurrent.*;

public class Main {

    private static final int BATCH_SIZE = 100;

    public static void main(String[] args) {

        long startTime = System.currentTimeMillis();

        try {

            File directory = new File("../Files");

            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println("Directory does not exist.");
                return;
            }

            File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

            if (files == null || files.length == 0) {
                System.out.println("No files found.");
                return;
            }

            Arrays.sort(files, (f1, f2) -> Long.compare(f2.lastModified(), f1.lastModified()));

            int totalFiles = files.length;

            System.out.println("Total Files Found: " + totalFiles);

            int grandTotalLines = 0;
            int grandTotalWords = 0;

            ExecutorService executor = Executors.newFixedThreadPool(4); // only once

            int batchNumber = 1;

            for (int start = 0; start < totalFiles; start += BATCH_SIZE) {

                int end = Math.min(start + BATCH_SIZE, totalFiles);

                System.out.println("\nProcessing Batch " + batchNumber +
                        " (Files " + (start + 1) + " to " + end + ")");

                List<Future<FileResult>> futures = new ArrayList<>();

                for (int i = start; i < end; i++) {

                    FileTask task = new FileTask(files[i]);

                    Future<FileResult> future = executor.submit(task);

                    futures.add(future);
                }

                int batchLines = 0;
                int batchWords = 0;

                for (Future<FileResult> future : futures) {

                    try {

                        FileResult result = future.get();

                        System.out.println("File: " + result.getFileName());
                        System.out.println("Lines: " + result.getLineCount());
                        System.out.println("Words: " + result.getWordCount());
                        System.out.println();

                        batchLines += result.getLineCount();
                        batchWords += result.getWordCount();

                    } catch (InterruptedException | ExecutionException e) {

                        System.out.println("Error processing file: " + e.getMessage());
                    }
                }

                System.out.println("Batch " + batchNumber + " Summary:");
                System.out.println("Lines: " + batchLines);
                System.out.println("Words: " + batchWords);

                grandTotalLines += batchLines;
                grandTotalWords += batchWords;

                batchNumber++;
            }

            executor.shutdown(); // shutdown only once

            System.out.println("\n----------------------------------");
            System.out.println("Final Summary");
            System.out.println("----------------------------------");
            System.out.println("Total Files Processed: " + totalFiles);
            System.out.println("Total Lines: " + grandTotalLines);
            System.out.println("Total Words: " + grandTotalWords);

        } catch (Exception e) {

            System.out.println("Unexpected error: " + e.getMessage());
        }

        long endTime = System.currentTimeMillis();

        System.out.println("\nTotal Execution Time: " + (endTime - startTime) + " ms");
    }
}