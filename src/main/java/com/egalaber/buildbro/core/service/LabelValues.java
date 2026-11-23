package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.api.model.ILabelDefinition;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Whether a label value fits a label definition. Comparison is lenient: ignoring case, and for
 * {@code BOOLEAN}, {@code INTEGER} and {@code URL} ignoring surrounding whitespace.
 */
public final class LabelValues {
    private static final Pattern INTEGER = Pattern.compile("[+-]?\\d+");

    private LabelValues() {
    }

    public static boolean fits(ILabelDefinition definition, String value) {
        if (value == null) {
            return false;
        }
        return switch (definition.type()) {
            case STRING -> true;
            case BOOLEAN -> "true".equalsIgnoreCase(value.trim()) || "false".equalsIgnoreCase(value.trim());
            case INTEGER -> INTEGER.matcher(value.trim()).matches();
            case URL -> isHttpUrl(value.trim());
            case ENUM -> definition.allowedValues().stream().anyMatch(value::equalsIgnoreCase);
            case REGEX -> definition.pattern() != null && compile(definition.pattern()).matcher(value).matches();
        };
    }

    /** Compiles a definition's pattern the way values are matched against it: ignoring case. */
    static Pattern compile(String pattern) {
        return Pattern.compile(pattern, Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
    }

    private static boolean isHttpUrl(String value) {
        try {
            URI uri = new URI(value);
            String scheme = uri.getScheme() == null ? "" : uri.getScheme().toLowerCase(Locale.ROOT);
            return (scheme.equals("http") || scheme.equals("https")) && uri.getHost() != null;
        } catch (URISyntaxException e) {
            return false;
        }
    }
}
