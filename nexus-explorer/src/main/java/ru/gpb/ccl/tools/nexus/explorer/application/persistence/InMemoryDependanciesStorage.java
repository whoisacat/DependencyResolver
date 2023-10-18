package ru.gpb.ccl.tools.nexus.explorer.application.persistence;

import ru.gpb.ccl.tools.nexus.explorer.application.domain.entity.Dependency;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class InMemoryDependanciesStorage implements DependanciesStorage {
    private final Set<Dependency> existedDependencies = new HashSet<>();
    private final Set<Dependency> newDependencies = new HashSet<>();
    private final Set<Dependency> errorCausesDependencies = new HashSet<>();

    @Override
    public Set<Dependency> getExistedDependencies() {
        return Collections.unmodifiableSet(existedDependencies);
    }

    @Override
    public Set<Dependency> getNewDependencies() {
        return Collections.unmodifiableSet(newDependencies);
    }

    @Override
    public Set<Dependency> getErrorCausesDependencies() {
        return Collections.unmodifiableSet(errorCausesDependencies);
    }

    @Override
    public void addToExisting(Dependency dependency) {
        existedDependencies.add(dependency);
    }

    @Override
    public void addToNew(Dependency dependency) {
        newDependencies.add(dependency);
    }

    @Override
    public void addToErrorCauses(Dependency dependency) {
        errorCausesDependencies.add(dependency);
    }
}
