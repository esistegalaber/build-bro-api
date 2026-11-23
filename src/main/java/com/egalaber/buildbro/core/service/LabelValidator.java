package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.fault.InvalidRequestException;
import com.egalaber.buildbro.api.model.ILabelDefinition;
import com.egalaber.buildbro.api.model.LabelTarget;
import com.egalaber.buildbro.api.model.LabelValidation;
import com.egalaber.buildbro.api.model.LabelViolationReason;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.function.Function;

/**
 * Checks labels against their label definitions before they are stored (issue #7). Only active when label
 * validation is {@code STRICT}; labels without a definition are always accepted.
 */
@Service
public class LabelValidator {
    private final LabelSettingsService labelSettingsService;
    private final LabelDefinitionService labelDefinitionService;

    public LabelValidator(LabelSettingsService labelSettingsService, LabelDefinitionService labelDefinitionService) {
        this.labelSettingsService = labelSettingsService;
        this.labelDefinitionService = labelDefinitionService;
    }

    /** A label to check. */
    public record Label(String key, String value) {
    }

    public void check(LabelTarget target, Map<String, String> labels) {
        check(target, labels.entrySet(), entry -> new Label(entry.getKey(), entry.getValue()));
    }

    public <T> void check(LabelTarget target, Collection<T> labels, Function<T, Label> toLabel) {
        if (labels.isEmpty() || labelSettingsService.validation() != LabelValidation.STRICT) {
            return;
        }
        Map<String, ILabelDefinition> definitions = labelDefinitionService.allByKey();
        List<String> problems = new ArrayList<>();
        for (T item : labels) {
            Label label = toLabel.apply(item);
            ILabelDefinition definition = label.key() == null ? null : definitions.get(label.key());
            if (definition != null) {
                LabelValues.violation(definition, target, label.value())
                        .map(reason -> problem(definition, target, label, reason))
                        .ifPresent(problems::add);
            }
        }
        if (!problems.isEmpty()) {
            throw new InvalidRequestException(String.join("; ", problems));
        }
    }

    private static String problem(ILabelDefinition definition, LabelTarget target, Label label, LabelViolationReason reason) {
        if (reason == LabelViolationReason.WRONG_TARGET) {
            return "Label '" + label.key() + "' is not meant for " + plural(target);
        }
        return "Label '" + label.key() + "=" + label.value() + "' does not fit its definition: expected "
                + LabelValues.expectation(definition);
    }

    private static String plural(LabelTarget target) {
        return target.getValue().toLowerCase(Locale.ROOT) + "s";
    }
}
