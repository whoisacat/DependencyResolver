package ru.gpb.ccl.tools.nexus.explorer.infrastructure;

class WrongValueInSettingsException extends RuntimeException {
    public WrongValueInSettingsException(String key) {
        super(String.format("wrong %s in settings", key));
    }
}
