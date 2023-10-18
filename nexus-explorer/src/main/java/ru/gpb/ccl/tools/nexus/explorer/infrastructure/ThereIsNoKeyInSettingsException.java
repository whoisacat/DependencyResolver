package ru.gpb.ccl.tools.nexus.explorer.infrastructure;

class ThereIsNoKeyInSettingsException extends RuntimeException {
    public ThereIsNoKeyInSettingsException(String key) {
        super(String.format("there is no %s in settings", key));
    }
}
