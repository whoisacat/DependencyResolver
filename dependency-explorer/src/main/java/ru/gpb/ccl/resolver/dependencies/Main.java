package ru.gpb.ccl.resolver.dependencies;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Main {

    private static final Logger logger = LoggerFactory.getLogger(Main.class.getSimpleName());

    public Main() {
    }

    public static void main(String[] args) {

        logger.error("");
        logger.info("");
        logger.warn("");
        logger.debug("");
        logger.trace("");

        Main app = new Main();
        app.run();
    }

    private void run() {
        String gradleBuildLogFile = "./gradle.build.log";
        String gradleBuildLogContent;
        try {
            gradleBuildLogContent = Files.readString(Path.of(gradleBuildLogFile));
        } catch (IOException e) {
            logger.error("cant read gradle build log file {}", gradleBuildLogFile, e);
            throw new RuntimeException(e);
        }
        String links = Arrays.stream(gradleBuildLogContent.split(System.lineSeparator()))
                .filter(it -> it.contains("Downloading http"))
                .map(it -> Arrays.stream(it.split(" "))
                        .filter(substring -> substring.startsWith("http"))
                        .findAny().orElseThrow())
                .collect(Collectors.joining("\n"));

        try {
            Files.writeString(Path.of("./dependencies.txt"), links);
        } catch (IOException e) {
            logger.error("cant wright to file {}", gradleBuildLogFile, e);
            throw new RuntimeException(e);
        }
    }
}
