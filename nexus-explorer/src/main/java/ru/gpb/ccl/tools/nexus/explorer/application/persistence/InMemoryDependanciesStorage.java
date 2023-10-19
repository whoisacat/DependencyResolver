package ru.gpb.ccl.tools.nexus.explorer.application.persistence;

import ru.gpb.ccl.tools.nexus.explorer.application.domain.entity.Dependency;

import java.util.*;

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

    @Override
    public String getAllString() {

        List<String> list = new LinkedList<>();

        list.add("existed:");
        list.addAll(existedDependencies.stream()
                .map(Object::toString)
                .toList());
        list.add("new:");
        list.addAll(newDependencies.stream()
                .map(Object::toString)
                .toList());
        list.add("error causes:");
        list.addAll(errorCausesDependencies.stream()
                .map(Object::toString)
                .toList());

        return String.join(System.lineSeparator(),list);
    }
}
