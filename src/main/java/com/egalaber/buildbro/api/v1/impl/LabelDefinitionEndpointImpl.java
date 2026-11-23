package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.ILabelDefinition;
import com.egalaber.buildbro.api.v1.LabelDefinitionsApi;
import com.egalaber.buildbro.core.service.LabelDefinitionService;
import jakarta.transaction.Transactional;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * Implements {@link LabelDefinitionsApi}, which is generated from the contract ({@code openapi/build-bro-api.yaml}).
 */
@RestController
@Transactional
public class LabelDefinitionEndpointImpl implements LabelDefinitionsApi {
    private final LabelDefinitionService labelDefinitionService;

    public LabelDefinitionEndpointImpl(LabelDefinitionService labelDefinitionService) {
        this.labelDefinitionService = labelDefinitionService;
    }

    @Override
    public List<ILabelDefinition> listLabelDefinitions() {
        return labelDefinitionService.all();
    }

    @Override
    public ILabelDefinition getLabelDefinition(String key) {
        return labelDefinitionService.byKey(key)
                .orElseThrow(() -> new DataNotFoundException("No label definition found for key='" + key + "'"));
    }

    @Override
    public ILabelDefinition saveLabelDefinition(String key, ILabelDefinition definition) {
        return labelDefinitionService.save(key, definition);
    }

    @Override
    public void deleteLabelDefinition(String key) {
        labelDefinitionService.delete(key);
    }
}
