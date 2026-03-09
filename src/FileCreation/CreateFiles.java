package FileCreation;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
class CreateFiles {
    CreateFiles() throws IOException {

        String folderPath = "C:\\Users\\KadamanchiPrakashRaj\\SB_Assignments\\Files\\";
        Files.createDirectories(Paths.get(folderPath));

        for (int i = 1; i <= 334; i++) {

            Path filePath = Paths.get(folderPath + "file" + i + ".txt");

            int lines = i % 5;   //number of lines in that file

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {

                for (int line = 1; line <= lines; line++) {

                    StringBuilder sb = new StringBuilder();

                    for (int word = 1; word <= line; word++) {

                        String randomWord = UUID.randomUUID()
                                .toString()
                                .replace("-", " ")
                                .split(" ")[0];

                        sb.append(randomWord);

                        if (word < line) {
                            sb.append(" ");
                        }
                    }

                    writer.write(sb.toString());
                    writer.newLine();
                }
            }
        }

        System.out.println("Files created successfully!");
    }
}