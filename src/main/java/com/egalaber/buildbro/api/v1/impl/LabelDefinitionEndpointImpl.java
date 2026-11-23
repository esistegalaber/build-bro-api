package com.egalaber.buildbro.api.v1.impl;

import com.egalaber.buildbro.api.fault.DataNotFoundException;
import com.egalaber.buildbro.api.model.ILabelDefinition;
import com.egalaber.buildbro.api.model.ILabelDefinitionChange;
import com.egalaber.buildbro.api.model.ILabelSettings;
import com.egalaber.buildbro.api.model.ILabelViolation;
import com.egalaber.buildbro.api.v1.LabelDefinitionsApi;
import com.egalaber.buildbro.core.service.LabelDefinitionService;
import com.egalaber.buildbro.core.service.LabelSettingsService;
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
    private final LabelSettingsService labelSettingsService;

    public LabelDefinitionEndpointImpl(LabelDefinitionService labelDefinitionService, LabelSettingsService labelSettingsService) {
        this.labelDefinitionService = labelDefinitionService;
        this.labelSettingsService = labelSettingsService;
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
    public ILabelDefinitionChange saveLabelDefinition(String key, ILabelDefinition definition, Boolean dryRun) {
        return labelDefinitionService.save(key, definition, Boolean.TRUE.equals(dryRun));
    }

    @Override
    public List<ILabelViolation> listLabelViolations() {
        return labelDefinitionService.violations();
    }

    @Override
    public ILabelSettings getLabelSettings() {
        return labelSettingsService.get();
    }

    @Override
    public ILabelSettings saveLabelSettings(ILabelSettings settings) {
        return labelSettingsService.save(settings);
    }

    @Override
    public void deleteLabelDefinition(String key) {
        labelDefinitionService.delete(key);
    }
}
