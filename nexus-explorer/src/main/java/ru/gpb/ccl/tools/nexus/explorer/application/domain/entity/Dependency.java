package ru.gpb.ccl.tools.nexus.explorer.application.domain.entity;

import ru.gpb.ccl.tools.nexus.explorer.tools.Validator;

public record Dependency(Group group, Artifact artifact, String version, String externalLink) {
    public Dependency(String group, String artifact, String version, String externalLink) {
        this(new Group(group), new Artifact(artifact), version, externalLink);
        if (!versionIsValid(version)) throw new RuntimeException(String.format("invalid version %s", version));
    }

    private boolean versionIsValid(String version) {
        return Validator.isVersionValid(version);
    }

    public record Group(String id) {
    }
    public record Artifact(String id) {
    }

    @Override
    public String toString() {
        return group.id + ":" + artifact.id + ":" + version + "\t " + externalLink;
    }
}
