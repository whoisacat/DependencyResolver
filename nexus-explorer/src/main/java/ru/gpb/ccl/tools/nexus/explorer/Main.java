package ru.gpb.ccl.tools.nexus.explorer;

import ru.gpb.ccl.tools.nexus.explorer.application.domain.entity.Dependency;
import ru.gpb.ccl.tools.nexus.explorer.application.domain.service.DependencyService;
import ru.gpb.ccl.tools.nexus.explorer.application.infrastructure.DependencyRepositoryClient;
import ru.gpb.ccl.tools.nexus.explorer.application.persistence.DependanciesStorage;
import ru.gpb.ccl.tools.nexus.explorer.application.persistence.InMemoryDependanciesStorage;
import ru.gpb.ccl.tools.nexus.explorer.infrastructure.DependenciesFileReader;
import ru.gpb.ccl.tools.nexus.explorer.infrastructure.DependencyNexusBasicClient;
import ru.gpb.ccl.tools.nexus.explorer.infrastructure.LocalFileSettingsStorage;
import ru.gpb.ccl.tools.nexus.explorer.infrastructure.SettingsStorage;

import java.util.List;

public class Main {

    public static void main(String[] args) {

        DependanciesStorage dependanciesStorage = new InMemoryDependanciesStorage();
        final SettingsStorage settingsStorage =
                new LocalFileSettingsStorage("settings.txt");//todo
        String repoUrl = settingsStorage.getRepoUrl();
        String username = settingsStorage.getUsername();
        String password = settingsStorage.getPassword();

        settingsStorage.getSslSettings().forEach(System::setProperty);

        new Main(new DependenciesFileReader("dependencies.txt"),//todo
                new DependencyService(),
                new DependencyNexusBasicClient(dependanciesStorage, repoUrl, username, password),
                dependanciesStorage).start();
    }

    private final DependanciesStorage dependenciesStorage;
    private final DependenciesFileReader dependenciesFileReader;
    private final DependencyService dependencyService;
    private final DependencyRepositoryClient dependencyRepositoryClient;

    public Main(DependenciesFileReader dependenciesFileReader, DependencyService dependencyService,
                DependencyRepositoryClient dependencyRepositoryClient, DependanciesStorage dependanciesStorage) {
        this.dependenciesStorage = dependanciesStorage;
        this.dependenciesFileReader = dependenciesFileReader;
        this.dependencyService = dependencyService;
        this.dependencyRepositoryClient = dependencyRepositoryClient;
    }

    private void start() {
        final List<String> dependenciesLinksList = dependenciesFileReader
                .readDependenciesFormExternalFile();
        List<Dependency> dependencies = dependenciesLinksList.stream()
                .map(dependencyService::createDependencyFromLink)
                .toList();

        dependencyRepositoryClient.checkExistenceInRepo(dependencies);

        System.out.println("existed:");
        dependenciesStorage.getExistedDependencies().forEach(System.out::println);
        System.out.println("new:");
        dependenciesStorage.getNewDependencies().forEach(System.out::println);
        System.out.println("error causes:");
        dependenciesStorage.getErrorCausesDependencies().forEach(System.out::println);
    }

}