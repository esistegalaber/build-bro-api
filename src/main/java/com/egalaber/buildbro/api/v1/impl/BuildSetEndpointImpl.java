package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.v1.BuildSetsApi;
import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.IBuildSet;
import com.egalaber.buildbro.api.model.IBuildSetTemplate;
import com.egalaber.buildbro.api.model.IBuildTemplate;
import com.egalaber.buildbro.core.service.BuildService;
import com.egalaber.buildbro.core.service.BuildSetTemplateService;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;

/**
 * Implements {@link BuildSetsApi}, which is generated from the contract ({@code openapi/build-bro-api.yaml}).
 */
@RestController
@Transactional
public class BuildSetEndpointImpl implements BuildSetsApi {
    private final BuildSetTemplateService buildSetTemplateService;
    private final BuildService buildService;

    public BuildSetEndpointImpl(BuildSetTemplateService buildSetTemplateService, BuildService buildService) {
        this.buildSetTemplateService = buildSetTemplateService;
        this.buildService = buildService;
    }

    @Override
    public List<String> listBuildSetNames() {
        return buildSetTemplateService.allBuildSetTemplateNames();
    }

    @Override
    public List<IBuildSetTemplate> listBuildSets() {
        return buildSetTemplateService.allBuildSetTemplates();
    }

    @Override
    public IBuildSetTemplate saveBuildSet(IBuildSetTemplate template) {
        return buildSetTemplateService.save(template);
    }

    @Override
    public IBuildSetTemplate getBuildSet(String name) {
        return buildSetTemplateService.byName(name)
                .orElseThrow(() -> new DataNotFoundException("No Environment found with name='" + name + "'"));
    }

    @Override
    public IBuildSet resolveBuildTemplates(Set<IBuildTemplate> buildTemplates) {
        return buildService.buildsOf(buildTemplates);
    }

    @Override
    public IBuildSet resolveBuildSet(String name) {
        return buildService.buildsOf(name);
    }

    @Override
    public void deleteBuildSet(String name) {
        buildSetTemplateService.delete(name);
    }
}
