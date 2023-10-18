package ru.gpb.ccl.tools.nexus.explorer.application.domain.service;

import ru.gpb.ccl.tools.nexus.explorer.application.domain.entity.Dependency;

public class DependencyService {
    public Dependency createDependencyFromLink(String link) {
        final String[] split = link.split("/");
        String[] dependencyData = new String[split.length - 5];
        System.arraycopy(split, 4, dependencyData, 0, dependencyData.length);
        String[] groupData = new String[dependencyData.length - 2];
        System.arraycopy(dependencyData, 0, groupData, 0, groupData.length);
        return new Dependency(String.join(".", groupData),
                dependencyData[dependencyData.length - 2],
                dependencyData[dependencyData.length - 1],
                link);
    }
}
