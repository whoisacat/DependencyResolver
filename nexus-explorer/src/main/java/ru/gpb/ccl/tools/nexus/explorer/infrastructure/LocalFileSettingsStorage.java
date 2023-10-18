package ru.gpb.ccl.tools.nexus.explorer.infrastructure;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.*;

public class LocalFileSettingsStorage implements SettingsStorage {
    private final String filePath;

    private static final String KEY_VALUE_SEPARATOR = ":::";
    private List<String> settingsStrings = null;

    public LocalFileSettingsStorage(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public String getRepoUrl() {
        return getSingleValue("url");
    }

    @Override
    public String getUsername() {
        return getSingleValue("username");
    }

    @Override
    public String getPassword() {
        return getSingleValue("password");
    }

    private String getSingleValue(String key) {
        final Optional<String> keyValueOptional = getSslSettingsStrings().stream()
                .filter(it -> it.startsWith(key + KEY_VALUE_SEPARATOR))
                .findAny();
        if (keyValueOptional.isEmpty()) throw new ThereIsNoKeyInSettingsException(key);
        final String[] keyValueSplit = keyValueOptional.get()
                .split(KEY_VALUE_SEPARATOR);
        if (keyValueSplit.length != 2 || keyValueSplit[1].isBlank()) throw new WrongValueInSettingsException(key);
        return keyValueSplit[1];
    }

    private Collection<String> getSslSettingsStrings() {
        if (settingsStrings != null) return settingsStrings;
        File file = new File(filePath);
        if (!file.exists()) {
            throw new RuntimeException(String.format("settings file %s doesnt exists", file.getAbsolutePath()));
        }
        try {
            String content = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            settingsStrings = Arrays.stream(content.split(System.lineSeparator())).toList();
        } catch (IOException e) {
            throw new RuntimeException(String.format("cant read file %s", file.getAbsolutePath()));
        }
        return settingsStrings;
    }

    @Override
    public Map<String, String> getSslSettings() {
        final Collection<String> settings = getSslSettingsStrings();
        final String sslKey = "ssl";
        final List<String> sslSettings = settings.stream()
                .filter(it -> it.contains(sslKey))
                .map(it -> it.replace(sslKey + KEY_VALUE_SEPARATOR, ""))
                .toList();
        Map<String, String> result = new HashMap<>();
        for (String sslSetting : sslSettings) {
            final String[] split = sslSetting.split("=");
            if (split.length != 2 || split[1].isBlank()) {
                throw new RuntimeException(String.format("wrong ssl property: %s",
                        String.join(" ", split)));
            }
            result.put(split[0], split[1]);
        }
        return result;
    }
}
