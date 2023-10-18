package ru.gpb.ccl.tools.nexus.explorer.application.infrastructure;

import ru.gpb.ccl.tools.nexus.explorer.application.domain.entity.Dependency;

import java.util.List;

public interface DependencyRepositoryClient {
    void checkExistenceInRepo(List<Dependency> dependencies);
}
