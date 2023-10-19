package ru.gpb.ccl.tools.nexus.explorer;

import ru.gpb.ccl.tools.nexus.explorer.application.domain.entity.Dependency;
import ru.gpb.ccl.tools.nexus.explorer.application.domain.service.DependencyService;
import ru.gpb.ccl.tools.nexus.explorer.application.infrastructure.DependencyRepositoryClient;
import ru.gpb.ccl.tools.nexus.explorer.application.persistence.DependanciesStorage;
import ru.gpb.ccl.tools.nexus.explorer.application.persistence.InMemoryDependanciesStorage;
import ru.gpb.ccl.tools.nexus.explorer.infrastructure.LocalFileReaderWriter;
import ru.gpb.ccl.tools.nexus.explorer.infrastructure.DependencyNexusBasicClient;
import ru.gpb.ccl.tools.nexus.explorer.infrastructure.LocalFileSettingsStorage;
import ru.gpb.ccl.tools.nexus.explorer.infrastructure.SettingsStorage;

import java.util.List;

public class Main {


    public static void main(String[] args) {

        DependanciesStorage dependanciesStorage = new InMemoryDependanciesStorage();

        final SettingsStorage settingsStorage =
                new LocalFileSettingsStorage(settingsFile);
        String repoUrl = settingsStorage.getRepoUrl();
        String username = settingsStorage.getUsername();
        String password = settingsStorage.getPassword();

        settingsStorage.getSslSettings().forEach(System::setProperty);

        new Main(new LocalFileReaderWriter(),
                new DependencyService(),
                new DependencyNexusBasicClient(dependanciesStorage, repoUrl, username, password),
                dependanciesStorage).start();
    }

    private final static String directory = "./files/";
    private final static String inputFile = directory + "dependencies.txt";
    private final static String outputFile = directory + "out%s.txt";
    private final static String settingsFile = directory + "settings.txt";
    private final DependanciesStorage dependenciesStorage;
    private final LocalFileReaderWriter localFileReaderWriter;
    private final DependencyService dependencyService;
    private final DependencyRepositoryClient dependencyRepositoryClient;

    public Main(LocalFileReaderWriter localFileReaderWriter, DependencyService dependencyService,
                DependencyRepositoryClient dependencyRepositoryClient, DependanciesStorage dependanciesStorage) {
        this.dependenciesStorage = dependanciesStorage;
        this.localFileReaderWriter = localFileReaderWriter;
        this.dependencyService = dependencyService;
        this.dependencyRepositoryClient = dependencyRepositoryClient;
    }

    private void start() {
        List<Dependency> dependencies = localFileReaderWriter.readLinesFormFile(inputFile)
                .stream()
                .map(dependencyService::createDependencyFromLink)
                .toList();

        dependencyRepositoryClient.checkExistenceInRepo(dependencies);

        String out = dependenciesStorage.getAllString();
        System.out.println(out);

        localFileReaderWriter.writeToFile(outputFile, out);
    }

}