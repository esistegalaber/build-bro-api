package com.egalaber.buildbro.api.impl;

import com.egalaber.buildbro.api.BuildSetEndpoint;
import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.fault.InvalidRequestException;
import com.egalaber.buildbro.api.model.IBuildSet;
import com.egalaber.buildbro.api.model.IBuildSetTemplate;
import com.egalaber.buildbro.api.model.IBuildTemplate;
import com.egalaber.buildbro.core.service.BuildSetTemplateService;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Set;

@RestController
@Transactional
public class BuildSetEndpointImpl implements BuildSetEndpoint {
    private final BuildSetTemplateService buildSetTemplateService;

    public BuildSetEndpointImpl(BuildSetTemplateService buildSetTemplateService) {
        this.buildSetTemplateService = buildSetTemplateService;
    }

    @Override
    public List<String> names() {
        return buildSetTemplateService.allBuildSetTemplateNames();
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
    public IBuildSet verify(Set<IBuildTemplate> artifacts) {
        return null;
    }

    @Override
    public IBuildSet environment(String name) throws DataNotFoundException {
        return null;
    }

    @Override
    public void delete(String name) {
        buildSetTemplateService.delete(name);
    }
}
