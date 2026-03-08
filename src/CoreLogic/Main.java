package CoreLogic;
import java.io.*;
import java.util.*;
public class Main {
    static void main() {
        long startTime = System.currentTimeMillis();

        try {

            // Path to files folder
            File directory = new File("../Files");

            if (!directory.exists() || !directory.isDirectory()) {
                System.out.println("Directory not found!");
                return;
            }

            File[] files = directory.listFiles((dir, name) -> name.endsWith(".txt"));

            if (files == null || files.length == 0) {
                System.out.println("No text files found.");
                return;
            }

            int totalFiles = 0;
            int totalLines = 0;
            int totalWords = 0;

            // process maximum 100 files
            int maxFiles = Math.min(files.length, 100);

            for (int i = 0; i < maxFiles; i++) {

                File file = files[i];

                int lineCount = 0;
                int wordCount = 0;

                BufferedReader br = new BufferedReader(new FileReader(file));
                String line;

                while ((line = br.readLine()) != null) {

                    lineCount++;

                    if (!line.trim().isEmpty()) {
                        String[] words = line.trim().split("\\s+");
                        wordCount += words.length;
                    }
                }

                br.close();

                System.out.println("File: " + file.getName());
                System.out.println("Lines: " + lineCount);
                System.out.println("Words: " + wordCount);
                System.out.println();

                totalLines += lineCount;
                totalWords += wordCount;
                totalFiles++;
            }

            System.out.println("----------------------------------");
            System.out.println("Summary");
            System.out.println("----------------------------------");
            System.out.println("Total Files Processed: " + totalFiles);
            System.out.println("Total Lines: " + totalLines);
            System.out.println("Total Words: " + totalWords);

            long endTime = System.currentTimeMillis();

            System.out.println("\nTotal Execution Time: " + (endTime - startTime) + " ms");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
