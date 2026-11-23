package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.model.ILabelSettings;
import com.egalaber.buildbro.api.model.LabelValidation;
import com.egalaber.buildbro.core.domain.Setting;
import com.egalaber.buildbro.core.repository.SettingRepository;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

/** The instance-wide label settings (issue #7). Without a stored value, validation is {@code OFF}. */
@Service
@Transactional
public class LabelSettingsService {
    static final String LABEL_VALIDATION = "label.validation";

    private final SettingRepository settingRepository;

    public LabelSettingsService(SettingRepository settingRepository) {
        this.settingRepository = settingRepository;
    }

    public ILabelSettings get() {
        return new ILabelSettings(validation());
    }

    public ILabelSettings save(ILabelSettings settings) {
        settingRepository.save(new Setting(LABEL_VALIDATION, settings.labelValidation().getValue()));
        return get();
    }

    public LabelValidation validation() {
        return settingRepository.findById(LABEL_VALIDATION)
                .map(setting -> LabelValidation.fromValue(setting.getValue()))
                .orElse(LabelValidation.OFF);
    }
}
