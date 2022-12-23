package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.v1.BuildSetEndpoint;
import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.fault.InvalidRequestException;
import com.egalaber.buildbro.api.model.IBuildSet;
import com.egalaber.buildbro.api.model.IBuildSetTemplate;
import com.egalaber.buildbro.api.model.IBuildTemplate;
import com.egalaber.buildbro.core.service.BuildService;
import com.egalaber.buildbro.core.service.BuildSetTemplateService;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;

import java.util.List;
import java.util.Set;

@RestController
@Transactional
public class BuildSetEndpointImpl implements BuildSetEndpoint {
    private final BuildSetTemplateService buildSetTemplateService;
    private final BuildService buildService;

    public BuildSetEndpointImpl(BuildSetTemplateService buildSetTemplateService, BuildService buildService) {
        this.buildSetTemplateService = buildSetTemplateService;
        this.buildService = buildService;
    }

    @Override
    public List<String> names() {
        return buildSetTemplateService.allBuildSetTemplateNames();
    }

    @Override
    public List<IBuildSetTemplate> all() {
        return buildSetTemplateService.allBuildSetTemplates();
    }

    @Override
    public IBuildSetTemplate save(IBuildSetTemplate template) throws InvalidRequestException {
        return buildSetTemplateService.save(template);
    }

    @Override
    public IBuildSetTemplate get(String name) throws DataNotFoundException {
        return buildSetTemplateService.byName(name)
                .orElseThrow(() -> new DataNotFoundException("No Environment found with name='" + name + "'"));
    }

    @Override
    public IBuildSet verify(Set<IBuildTemplate> buildTemplates) {
        return buildService.buildsOf(buildTemplates);
    }

    @Override
    public IBuildSet buildsOf(String name) throws DataNotFoundException {
        return buildService.buildsOf(name);
    }

    @Override
    public void delete(String name) {
        buildSetTemplateService.delete(name);
    }
}
