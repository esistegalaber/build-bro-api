package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.fault.InvalidRequestException;
import com.egalaber.buildbro.api.model.IBuild;
import com.egalaber.buildbro.api.model.IBuildSearch;
import com.egalaber.buildbro.api.model.IBuildSet;
import com.egalaber.buildbro.api.model.IBuildTemplate;
import com.egalaber.buildbro.core.domain.Build;
import com.egalaber.buildbro.core.domain.BuildLabel;
import com.egalaber.buildbro.core.domain.BuildSetTemplate;
import com.egalaber.buildbro.core.domain.BuildTemplate;
import com.egalaber.buildbro.core.mapping.BuildMapper;
import com.egalaber.buildbro.core.mapping.BuildTemplateMapper;
import com.egalaber.buildbro.core.repository.*;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;

@Service
public class BuildService {
    private final BuildRepository buildRepository;
    private final BuildLabelRepository buildLabelRepository;
    private final BuildSetTemplateRepository buildSetTemplateRepository;

    public BuildService(BuildRepository buildRepository, BuildLabelRepository buildLabelRepository, BuildSetTemplateRepository buildSetTemplateRepository) {
        this.buildRepository = buildRepository;
        this.buildLabelRepository = buildLabelRepository;
        this.buildSetTemplateRepository = buildSetTemplateRepository;
    }

    public Optional<IBuild> byId(Long id) {
        return buildRepository.findById(id).map(BuildMapper::toApi);
    }

    public IBuild create(IBuild toCreate) throws InvalidRequestException {
        Optional<Build> alreadyExisting = of(toCreate.getProject(), toCreate.getBranch(), toCreate.getBuildNumber());
        if (alreadyExisting.isPresent()) {
            throw new InvalidRequestException("Build with project='" + toCreate.getProject() + "' branch='" + toCreate.getBranch() + "' buildNumber='" + toCreate.getBuildNumber() + "' already exists.");
        }
        Build newBuild = new Build(toCreate.getProject(), toCreate.getBranch(), toCreate.getBuildNumber());
        toCreate.getLabels().forEach(buildLabel ->
                newBuild.getLabels().add(
                        new BuildLabel(newBuild, buildLabel.getKey(), buildLabel.getValue()))
        );
        return BuildMapper.toApi(buildRepository.save(newBuild));
    }

    public IBuild addLabels(Long buildId, Map<String, String> labels) throws DataNotFoundException {
        final Build theBuild = buildRepository.findById(buildId)
                .orElseThrow(() -> new DataNotFoundException("No Build found for buildId='" + buildId + "'"));
        labels.forEach((key, value) ->
                theBuild.getLabels().add(new BuildLabel(theBuild, key, value)
                ));
        return BuildMapper.toApi(buildRepository.save(theBuild));
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
                labelsToSearch.forEach(label -> subSpec.set(subSpec.get().or(BuildSpecs.hasLabel(label))));
                theQuerySpecification = theQuerySpecification.and(subSpec.get());
            }
        }

        if (!ObjectUtils.isEmpty(search.getProject())) {
            theQuerySpecification = theQuerySpecification.and(
                    BuildSpecs.ofProject(search.getProject())
            );
        }
        if (!ObjectUtils.isEmpty(search.getBranch())) {
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

    /**
     * Fetch a {@link Build} based on the provided {@link com.egalaber.buildbro.core.domain.BuildTemplate}.
     * <p>
     * If the artifact includes a build number attempt to find
     * the build with said build number.
     * <p>
     * If there is no build number set on the artifact, the
     * latest build will be fetched
     *
     * @param template an BuildTemplate that contains info related to
     *                 a specific Build
     * @return the associated Build or "empty" if no Build was found
     */
    Optional<IBuild> withBuildNumberOrLatest(IBuildTemplate template) {
        IBuildSearch theSearch = IBuildSearch.fromBuildTemplate(template);
        Page<Build> searchResult = buildRepository.findAll(toSpecification(theSearch), theSearch.page());
        return searchResult.getContent()
                .stream()
                .findFirst()
                .map(BuildMapper::toApi);
    }

    Optional<IBuild> withBuildNumberOrLatest(BuildTemplate template) {
        return withBuildNumberOrLatest(BuildTemplateMapper.toApi(template));
    }

    public IBuildSet buildsOf(Set<IBuildTemplate> buildTemplates) {
        IBuildSet buildSet = new IBuildSet();
        buildSet.setName("verification-result");
        buildTemplates
                .forEach(template -> withBuildNumberOrLatest(template).ifPresent(
                        theBuild -> buildSet.getBuilds().put(template.getProject(), theBuild)
                ));
        return buildSet;
    }


    public IBuildSet buildsOf(String environmentName) throws DataNotFoundException {
        BuildSetTemplate buildSetTemplate = buildSetTemplateRepository.findOne(
                BuildSetTemplateSpecs.byName(environmentName)
        ).orElseThrow(() -> new DataNotFoundException("No Environment found with name='${environmentName}'"));
        IBuildSet buildSet = new IBuildSet();
        buildSet.setName(buildSetTemplate.getName());

        buildSetTemplate.getBuildTemplates()
                .forEach(template -> withBuildNumberOrLatest(template).ifPresent(
                        theBuild -> buildSet.getBuilds().put(template.getProject(), theBuild)
                ));
        return buildSet;
    }
}
