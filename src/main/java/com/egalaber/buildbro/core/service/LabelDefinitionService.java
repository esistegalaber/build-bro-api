package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.fault.InvalidRequestException;
import com.egalaber.buildbro.api.model.ILabelDefinition;
import com.egalaber.buildbro.api.model.LabelStyle;
import com.egalaber.buildbro.api.model.LabelTarget;
import com.egalaber.buildbro.api.model.LabelType;
import com.egalaber.buildbro.core.domain.LabelDefinition;
import com.egalaber.buildbro.core.mapping.LabelDefinitionMapper;
import com.egalaber.buildbro.core.repository.LabelDefinitionRepository;
import com.egalaber.buildbro.core.repository.LabelDefinitionSpecs;
import jakarta.transaction.Transactional;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.EnumSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.PatternSyntaxException;
import java.util.stream.StreamSupport;

/**
 * Manages the optional label definitions (issue #7). Keys are unique ignoring case.
 */
@Service
@Transactional
public class LabelDefinitionService {
    private final LabelDefinitionRepository labelDefinitionRepository;

    public LabelDefinitionService(LabelDefinitionRepository labelDefinitionRepository) {
        this.labelDefinitionRepository = labelDefinitionRepository;
    }

    public List<ILabelDefinition> all() {
        return StreamSupport.stream(labelDefinitionRepository.findAll(Sort.by("key")).spliterator(), false)
                .map(LabelDefinitionMapper::toApi)
                .toList();
    }

    public Optional<ILabelDefinition> byKey(String key) {
        return find(key).map(LabelDefinitionMapper::toApi);
    }

    /**
     * Creates the definition of {@code key}, or replaces the existing one. The key of {@code definition} is
     * ignored; the stored key takes the spelling of {@code key}.
     */
    public ILabelDefinition save(String key, ILabelDefinition definition) {
        ILabelDefinition normalised = validated(key, definition);
        LabelDefinition toSave = find(normalised.key()).orElseGet(LabelDefinition::new);
        LabelDefinitionMapper.copy(normalised, toSave);
        return LabelDefinitionMapper.toApi(labelDefinitionRepository.save(toSave));
    }

    public void delete(String key) {
        find(key).ifPresent(labelDefinitionRepository::delete);
    }

    private Optional<LabelDefinition> find(String key) {
        return labelDefinitionRepository.findOne(LabelDefinitionSpecs.byKey(key));
    }

    /** The definition with the key from the path and defaults applied, or an {@link InvalidRequestException}. */
    private static ILabelDefinition validated(String key, ILabelDefinition definition) {
        String theKey = key == null ? "" : key.trim();
        if (theKey.isEmpty()) {
            throw new InvalidRequestException("A label definition needs a key");
        }
        List<LabelTarget> appliesTo = definition.appliesTo().isEmpty()
                ? List.of(LabelTarget.values())
                : List.copyOf(EnumSet.copyOf(definition.appliesTo())); // in enum order, without duplicates
        ILabelDefinition normalised = new ILabelDefinition(
                theKey,
                definition.displayName(),
                definition.description(),
                appliesTo,
                definition.type(),
                definition.allowedValues(),
                StringUtils.hasText(definition.pattern()) ? definition.pattern() : null,
                definition.style(),
                definition.valueStyles()
        );
        checkAllowedValues(normalised);
        checkPattern(normalised);
        checkValueStyles(normalised);
        return normalised;
    }

    private static void checkAllowedValues(ILabelDefinition definition) {
        List<String> values = definition.allowedValues();
        if (definition.type() != LabelType.ENUM) {
            if (!values.isEmpty()) {
                throw invalid(definition, "allowedValues are only allowed for type ENUM");
            }
            return;
        }
        if (values.isEmpty()) {
            throw invalid(definition, "type ENUM needs allowedValues");
        }
        Set<String> seen = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (String value : values) {
            if (!StringUtils.hasText(value)) {
                throw invalid(definition, "allowedValues must not be blank");
            }
            if (!seen.add(value)) {
                throw invalid(definition, "allowedValues contain '" + value + "' twice (ignoring case)");
            }
        }
    }

    private static void checkPattern(ILabelDefinition definition) {
        if (definition.type() != LabelType.REGEX) {
            if (definition.pattern() != null) {
                throw invalid(definition, "a pattern is only allowed for type REGEX");
            }
            return;
        }
        if (definition.pattern() == null) {
            throw invalid(definition, "type REGEX needs a pattern");
        }
        try {
            LabelValues.compile(definition.pattern());
        } catch (PatternSyntaxException e) {
            throw invalid(definition, "pattern '" + definition.pattern() + "' is not a valid regular expression: " + e.getDescription());
        }
    }

    private static void checkValueStyles(ILabelDefinition definition) {
        Set<String> seen = new TreeSet<>(String.CASE_INSENSITIVE_ORDER);
        for (Map.Entry<String, LabelStyle> entry : definition.valueStyles().entrySet()) {
            String value = entry.getKey();
            if (entry.getValue() == null) {
                throw invalid(definition, "valueStyles has no style for '" + value + "'");
            }
            if (!seen.add(value)) {
                throw invalid(definition, "valueStyles contain '" + value + "' twice (ignoring case)");
            }
            if (!LabelValues.fits(definition, value)) {
                throw invalid(definition, "valueStyles name '" + value + "', which is not a valid "
                        + definition.type().getValue().toLowerCase(Locale.ROOT) + " value of this label");
            }
        }
    }

    private static InvalidRequestException invalid(ILabelDefinition definition, String problem) {
        return new InvalidRequestException("Label definition '" + definition.key() + "': " + problem);
    }
}
