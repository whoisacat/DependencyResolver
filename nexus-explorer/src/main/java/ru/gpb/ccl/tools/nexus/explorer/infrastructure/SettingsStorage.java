package ru.gpb.ccl.tools.nexus.explorer.infrastructure;

import java.util.Map;

public interface SettingsStorage {
    String getRepoUrl();

    String getUsername();

    String getPassword();

    Map<String, String> getSslSettings();
}
