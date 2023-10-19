package ru.gpb.ccl.tools.nexus.explorer.infrastructure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class LocalFileReaderWriter {

    public List<String> readLinesFormFile(String inputFile) {
        File file = new File(inputFile);
        if (!file.exists()) throw new RuntimeException(String.format("file %s doesnt exist", file.getAbsolutePath()));
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(String.format("cant read file %s", file.getAbsolutePath()), e);
        }
    }

    public void writeToFile(String outputFile, String out) {
        File file = new File(outputFile);
        if (file.exists()) {
            if (!file.delete()) throw new RuntimeException(String.format("cant clear file %s", file.getAbsolutePath()));
        }
        try {
            if (!file.createNewFile()) {
                throw new RuntimeException(String.format("cant create file %s", file.getAbsolutePath()));
            }
            Files.writeString(file.toPath(), out);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
