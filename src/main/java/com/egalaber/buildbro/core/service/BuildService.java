package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IBuildSearch;
import com.egalaber.buildbro.core.domain.Build;
import com.egalaber.buildbro.core.domain.BuildLabel;
import com.egalaber.buildbro.core.repository.BuildLabelRepository;
import com.egalaber.buildbro.core.repository.BuildLabelSpecs;
import com.egalaber.buildbro.core.repository.BuildRepository;
import com.egalaber.buildbro.core.repository.BuildSpecs;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BuildService {
    private final BuildRepository buildRepository;
    private final BuildLabelRepository buildLabelRepository;

    public BuildService(BuildRepository buildRepository, BuildLabelRepository buildLabelRepository) {
        this.buildRepository = buildRepository;
        this.buildLabelRepository = buildLabelRepository;
    }

    public Optional<Build> byId(Long id) {
        return buildRepository.findById(id);
    }

    public Build create(String project, String branch, Long buildNumber) {
        Build build = of(project, branch, buildNumber).orElse(
                new Build(project, branch, buildNumber)
        );
        return buildRepository.save(build);
    }

    public Build addLabels(Long buildId, Map<String, String> labels) throws DataNotFoundException {
        final Build build = byId(buildId)
                .orElseThrow(() -> new DataNotFoundException("No Build found for buildId='" + buildId + "'"));
        labels.forEach((key, value) ->
                build.getLabels().add(new BuildLabel(build, key, value)
                ));
        return buildRepository.save(build);
    }

    private Optional<Build> of(String project, String branch, Long buildNumber) {
        return buildRepository.findOne(
                BuildSpecs.findSpecificBuild(project, branch, buildNumber)
        );
    }

    public Page<Build> search(IBuildSearch search) {
        return buildRepository.findAll(toSpecification(search), search.page());
    }

    private Specification<Build> toSpecification(IBuildSearch search) {
        Specification<Build> theQuerySpecification = BuildSpecs.allBuilds();
        if (!search.getLabels().isEmpty()) {
            Set<BuildLabel> labelsToSearch = labelsToInclude(search.getLabels());
            if (labelsToSearch.isEmpty()) {
                return BuildSpecs.noBuilds();
            } else {
                final AtomicReference<Specification<Build>> subSpec = new AtomicReference<>(BuildSpecs.noBuilds());
                labelsToSearch.forEach((label) -> subSpec.set(subSpec.get().or(BuildSpecs.hasLabel(label))));
                theQuerySpecification = theQuerySpecification.and(subSpec.get());
            }
        }

        if (search.getProject() != null) {
            theQuerySpecification = theQuerySpecification.and(
                    BuildSpecs.ofProject(search.getProject())
            );
        }
        if (search.getBranch() != null) {
            theQuerySpecification = theQuerySpecification.and(
                    BuildSpecs.onBranch(search.getBranch())
            );
        }
        if (search.getMinBuildNumber() != null) {
            theQuerySpecification = theQuerySpecification.and(
                    BuildSpecs.minBuildNumber(search.getMinBuildNumber())
            );
        }
        if (search.getMaxBuildNumber() != null) {
            theQuerySpecification = theQuerySpecification.and(
                    BuildSpecs.maxBuildNumber(search.getMaxBuildNumber())
            );
        }
        return theQuerySpecification;
    }

    private Set<BuildLabel> labelsToInclude(Map<String, String> labels) {
        Set<BuildLabel> toReturn = new HashSet<>();
        labels.forEach((key, value) -> toReturn.addAll(
                buildLabelRepository.findAll(BuildLabelSpecs.labelsWithKeyAndValue(key, value))
        ));
        return toReturn;
    }

//    /**
//     * Fetch a {@link Build} based on the provided {@link Artifact}.
//     * <p>
//     * If the artifact includes a build number attempt to find
//     * the build with said build number.
//     * <p>
//     * If there is no build number set on the artifact, the
//     * latest build will be fetched
//     *
//     * @param artifact an artifact that contains info related to
//     *                 a specific Build
//     * @return the associated Build or "empty" if no Build was found
//     */
//    Optional<Build> withBuildNumberOrLatestArtifact(Artifact artifact) {
//        BuildSearch theSearch = BuildSearch.fromArtifact(artifact)
//        Page<Build> searchResult = buildRepository.findAll(toSpecification(theSearch), theSearch.page())
//        searchResult.isEmpty() ? Optional.empty() : Optional.of(searchResult.getContent().first())
//    }
//

//    EnvironmentBuilds ofEnvironment(String environmentName) throws DataNotFoundException {
//        Optional<Environment> environment = environmentRepository.findOne(environmentWithName(environmentName))
//        if (environment.isEmpty()) {
//            throw new DataNotFoundException("No Environment found with name='${environmentName}'")
//        }
//        Environment theEnv = environment.get()
//        EnvironmentBuilds toReturn = new EnvironmentBuilds(
//                environment:theEnv.name,
//                internal:theEnv.internal
//        )
//        theEnv.artifacts.forEach({Artifact theArtifact ->
//                withBuildNumberOrLatestArtifact(theArtifact)
//                        .ifPresent({Build build ->
//                                toReturn.add(build)
//                        })
//        })
//        toReturn
//    }
}
