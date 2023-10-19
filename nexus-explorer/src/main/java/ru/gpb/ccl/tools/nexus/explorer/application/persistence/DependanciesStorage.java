package ru.gpb.ccl.tools.nexus.explorer.application.persistence;

import ru.gpb.ccl.tools.nexus.explorer.application.domain.entity.Dependency;

import java.util.Set;

public interface DependanciesStorage {

    Set<Dependency> getExistedDependencies();

    Set<Dependency> getNewDependencies();

    Set<Dependency> getErrorCausesDependencies();

    void addToExisting(Dependency dependency);

    void addToNew(Dependency dependency);

    void addToErrorCauses(Dependency dependency);

    String getAllString();
}
