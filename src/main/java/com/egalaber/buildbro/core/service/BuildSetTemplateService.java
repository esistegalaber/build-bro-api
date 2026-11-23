package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.fault.InvalidRequestException;
import com.egalaber.buildbro.api.model.IBuildSetTemplate;
import com.egalaber.buildbro.core.domain.BuildSetTemplate;
import com.egalaber.buildbro.core.domain.BuildTemplate;
import com.egalaber.buildbro.core.mapping.BuildSetTemplateMapper;
import com.egalaber.buildbro.core.mapping.BuildTemplateMapper;
import com.egalaber.buildbro.core.repository.BuildSetTemplateRepository;
import com.egalaber.buildbro.core.repository.BuildSetTemplateSpecs;
import com.egalaber.buildbro.core.repository.BuildTemplateRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.StreamSupport;

@Service
@Transactional
public class BuildSetTemplateService {
    private final BuildSetTemplateRepository buildSetTemplateRepository;
    private final BuildTemplateRepository buildTemplateRepository;

    public BuildSetTemplateService(BuildSetTemplateRepository buildSetTemplateRepository, BuildTemplateRepository buildTemplateRepository) {
        this.buildSetTemplateRepository = buildSetTemplateRepository;
        this.buildTemplateRepository = buildTemplateRepository;
    }

    public List<String> allBuildSetTemplateNames() {
        return buildSetTemplateRepository.allNames();
    }

    public Optional<IBuildSetTemplate> byName(String name) {
        return buildSetTemplateName(name)
                .map(BuildSetTemplateMapper::toApi);
    }

    public Optional<BuildSetTemplate> buildSetTemplateName(String name) {
        return buildSetTemplateRepository.findOne(BuildSetTemplateSpecs.byName(name));
    }

    public IBuildSetTemplate save(IBuildSetTemplate template) {
        BuildSetTemplate theTemplate;
        if (ObjectUtils.isEmpty(template.id())) {
            // new template case
            theTemplate = create(template);
        } else {
            // update template case
            theTemplate = update(template);
        }
        List<BuildTemplate> buildTemplates = template.buildTemplates().stream()
                .map(BuildTemplateMapper::toDomain)
                .toList();
        theTemplate.getBuildTemplates().addAll(buildTemplates);
        return BuildSetTemplateMapper.toApi(
                buildSetTemplateRepository.save(theTemplate)
        );
    }

    private BuildSetTemplate create(IBuildSetTemplate template) {
        if (byName(template.name()).isPresent()) {
            throw new InvalidRequestException("BuildSetTemplate with name='" + template.name() + "' already exists");
        }
        BuildSetTemplate toCreate = new BuildSetTemplate();
        toCreate.setName(template.name());
        return buildSetTemplateRepository.save(toCreate);
    }

    private BuildSetTemplate update(IBuildSetTemplate template) {
        BuildSetTemplate toUpdate = buildSetTemplateRepository.findById(template.id())
                .orElseThrow(() -> new InvalidRequestException("BuildSetTemplate with id='" + template.id() + "' not found"));
        toUpdate.setName(template.name());
        buildTemplateRepository.deleteAllById(
                toUpdate.getBuildTemplates().stream()
                        .map(BuildTemplate::getId)
                        .toList()
        );
        toUpdate.getBuildTemplates().clear();
        return buildSetTemplateRepository.save(toUpdate);
    }

    public void delete(String buildSetName) {
        buildSetTemplateName(buildSetName)
                .ifPresent(buildSetTemplateRepository::delete);
    }

    public List<IBuildSetTemplate> allBuildSetTemplates() {
        return StreamSupport.stream(buildSetTemplateRepository.findAll().spliterator(), false)
                .map(BuildSetTemplateMapper::toApi)
                .toList();
    }
}
