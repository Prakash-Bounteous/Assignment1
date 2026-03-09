package CoreLogic;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.concurrent.Callable;

class FileTask implements Callable<FileResult> {

    private File file;

    public FileTask(File file) {
        this.file = file;
    }

    @Override
    public FileResult call() throws Exception {

        int lines = 0;
        int words = 0;

        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;

        while ((line = br.readLine()) != null) {

            lines++;

            if (!line.trim().isEmpty()) {
                String[] arr = line.trim().split("\\s+");
                words += arr.length;
            }
        }

        br.close();

        return new FileResult(file.getName(), lines, words);
    }
}