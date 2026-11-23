package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.model.ILabelDefinition;
import com.egalaber.buildbro.api.model.ILabelViolation;
import com.egalaber.buildbro.api.model.LabelTarget;
import com.egalaber.buildbro.api.model.LabelViolationReason;
import com.egalaber.buildbro.core.repository.BuildLabelRepository;
import com.egalaber.buildbro.core.repository.DeploymentLabelRepository;
import com.egalaber.buildbro.core.repository.LabelKeyUsage;
import com.egalaber.buildbro.core.repository.LabelUsage;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Finds stored labels that do not fit their label definition (the conformance report of issue #7). Labels on an
 * entity their definition does not apply to are reported once per key, without a value.
 */
@Service
@Transactional
public class LabelViolationService {
    private static final Comparator<ILabelViolation> ORDER = Comparator
            .comparing(ILabelViolation::key, String.CASE_INSENSITIVE_ORDER)
            .thenComparing(ILabelViolation::target)
            .thenComparing(ILabelViolation::value, Comparator.nullsFirst(String.CASE_INSENSITIVE_ORDER));

    private final BuildLabelRepository buildLabelRepository;
    private final DeploymentLabelRepository deploymentLabelRepository;

    public LabelViolationService(BuildLabelRepository buildLabelRepository, DeploymentLabelRepository deploymentLabelRepository) {
        this.buildLabelRepository = buildLabelRepository;
        this.deploymentLabelRepository = deploymentLabelRepository;
    }

    /** Violations of {@code definitions}, ordered by key, target and value. */
    public List<ILabelViolation> violationsOf(Collection<ILabelDefinition> definitions) {
        if (definitions.isEmpty()) {
            return List.of();
        }
        Map<String, ILabelDefinition> byKey = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        definitions.forEach(definition -> byKey.put(definition.key(), definition));
        List<String> lowerCaseKeys = byKey.keySet().stream().map(key -> key.toLowerCase(Locale.ROOT)).toList();

        List<ILabelViolation> violations = new ArrayList<>();
        violations.addAll(wrongTarget(byKey, LabelTarget.BUILD, buildLabelRepository.keyUsagesOf(lowerCaseKeys)));
        violations.addAll(invalidValues(byKey, LabelTarget.BUILD, buildLabelRepository.usagesOf(lowerCaseKeys)));
        violations.addAll(wrongTarget(byKey, LabelTarget.DEPLOYMENT, deploymentLabelRepository.keyUsagesOf(lowerCaseKeys)));
        violations.addAll(invalidValues(byKey, LabelTarget.DEPLOYMENT, deploymentLabelRepository.usagesOf(lowerCaseKeys)));
        violations.sort(ORDER);
        return violations;
    }

    /** Keys on {@code target} whose definition does not apply to it, counting each entity once. */
    private static List<ILabelViolation> wrongTarget(Map<String, ILabelDefinition> definitions, LabelTarget target, List<LabelKeyUsage> usages) {
        return usages.stream()
                .filter(usage -> !definitions.get(usage.key()).appliesTo().contains(target))
                .map(usage -> new ILabelViolation(definitions.get(usage.key()).key(), null, target,
                        LabelViolationReason.WRONG_TARGET, usage.count()))
                .toList();
    }

    /** Values on {@code target} that its definition does not allow; keys on the wrong target are left out. */
    private static List<ILabelViolation> invalidValues(Map<String, ILabelDefinition> definitions, LabelTarget target, List<LabelUsage> usages) {
        List<ILabelViolation> violations = new ArrayList<>();
        for (LabelUsage usage : usages) {
            ILabelDefinition definition = definitions.get(usage.key());
            Optional<LabelViolationReason> reason = LabelValues.violation(definition, target, usage.value());
            if (reason.isPresent() && reason.get() == LabelViolationReason.INVALID_VALUE) {
                violations.add(new ILabelViolation(definition.key(), usage.value(), target, reason.get(), usage.count()));
            }
        }
        return violations;
    }
}
