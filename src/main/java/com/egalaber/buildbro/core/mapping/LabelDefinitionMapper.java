package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.ILabelDefinition;
import com.egalaber.buildbro.api.model.LabelStyle;
import com.egalaber.buildbro.api.model.LabelTarget;
import com.egalaber.buildbro.api.model.LabelType;
import com.egalaber.buildbro.core.domain.LabelDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class LabelDefinitionMapper {
    private LabelDefinitionMapper() {
    }

    public static ILabelDefinition toApi(LabelDefinition definition) {
        List<LabelTarget> appliesTo = new ArrayList<>();
        if (Boolean.TRUE.equals(definition.getAppliesToBuild())) {
            appliesTo.add(LabelTarget.BUILD);
        }
        if (Boolean.TRUE.equals(definition.getAppliesToDeployment())) {
            appliesTo.add(LabelTarget.DEPLOYMENT);
        }
        Map<String, LabelStyle> valueStyles = new TreeMap<>(String.CASE_INSENSITIVE_ORDER);
        definition.getValueStyles().forEach((value, style) -> valueStyles.put(value, LabelStyle.fromValue(style)));
        return new ILabelDefinition(
                definition.getKey(),
                definition.getDisplayName(),
                definition.getDescription(),
                appliesTo,
                LabelType.fromValue(definition.getType()),
                new ArrayList<>(definition.getAllowedValues()), // copies, not the persistent collections
                definition.getPattern(),
                definition.getStyle() == null ? null : LabelStyle.fromValue(definition.getStyle()),
                valueStyles
        );
    }

    /**
     * Copies {@code source} onto {@code target}, replacing its values and collections. Expects a definition
     * normalised by the service: {@code appliesTo} not empty.
     */
    public static void copy(ILabelDefinition source, LabelDefinition target) {
        target.setKey(source.key());
        target.setDisplayName(source.displayName());
        target.setDescription(source.description());
        target.setAppliesToBuild(source.appliesTo().contains(LabelTarget.BUILD));
        target.setAppliesToDeployment(source.appliesTo().contains(LabelTarget.DEPLOYMENT));
        target.setType(source.type().getValue());
        target.setPattern(source.pattern());
        target.setStyle(source.style() == null ? null : source.style().getValue());
        target.getAllowedValues().clear();
        target.getAllowedValues().addAll(source.allowedValues());
        target.getValueStyles().clear();
        source.valueStyles().forEach((value, style) -> target.getValueStyles().put(value, style.getValue()));
    }
}
