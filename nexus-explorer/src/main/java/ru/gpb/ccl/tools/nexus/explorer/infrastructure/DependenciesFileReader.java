package ru.gpb.ccl.tools.nexus.explorer.infrastructure;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class DependenciesFileReader {
    private final String pathToFile;

    public DependenciesFileReader(String pathToFile) {
        this.pathToFile = pathToFile;
    }

    public List<String> readDependenciesFormExternalFile() {
        File file = new File(pathToFile);
        if (!file.exists()) throw new RuntimeException(String.format("file %s doesnt exist", file.getAbsolutePath()));
        try {
            return Files.readAllLines(file.toPath());
        } catch (IOException e) {
            throw new RuntimeException(String.format("cant read file %s", file.getAbsolutePath()), e);
        }
    }
}
