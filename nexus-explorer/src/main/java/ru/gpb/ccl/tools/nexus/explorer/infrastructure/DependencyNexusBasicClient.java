package ru.gpb.ccl.tools.nexus.explorer.infrastructure;

import ru.gpb.ccl.tools.nexus.explorer.application.domain.entity.Dependency;
import ru.gpb.ccl.tools.nexus.explorer.application.infrastructure.DependencyRepositoryClient;
import ru.gpb.ccl.tools.nexus.explorer.application.persistence.DependanciesStorage;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Base64;
import java.util.List;

public class DependencyNexusBasicClient implements DependencyRepositoryClient {
    private final DependanciesStorage dependanciesStorage;
    private final String repoUrl;
    private final String username;
    private final String password;

    public DependencyNexusBasicClient(DependanciesStorage dependanciesStorage, String repoUrl, String username,
                                      String password) {
        this.dependanciesStorage = dependanciesStorage;
        this.repoUrl = repoUrl;
        this.username = username;
        this.password = password;
    }

    @Override
    public void checkExistenceInRepo(List<Dependency> dependencies) {
        dependencies.forEach(this::checkDependencyExistInRepo);
    }

    private void checkDependencyExistInRepo(Dependency dependency) {
        HttpClient httpClient = HttpClient.newHttpClient();
        URI targetUri;
        try {
            targetUri = new URI(repoUrl
                    + String.join("/", dependency.group().id().split("\\."))
                    + "/" + dependency.artifact().id()
                    + "/" + dependency.version()
                    + "/" + dependency.artifact().id() + "-" + dependency.version() + ".pom");
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }

        String secret = Base64.getEncoder().encodeToString(String.join(":", username, password).getBytes());
        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(targetUri)
                .header("accept", "application/json")
                .header("Content-Type", "multipart/form-data")
                .header("Authorization", "Basic " + secret)
                .GET()
                .build();
        try {
            HttpResponse<String> response = httpClient.send(httpRequest, HttpResponse.BodyHandlers.ofString());
            switch (response.statusCode()) {
                case 200 -> dependanciesStorage.addToExisting(dependency);
                case 404 -> dependanciesStorage.addToNew(dependency);
                default -> dependanciesStorage.addToErrorCauses(dependency);
            }
        } catch (IOException | InterruptedException e) {
            dependanciesStorage.addToErrorCauses(dependency);
        }
    }
}
