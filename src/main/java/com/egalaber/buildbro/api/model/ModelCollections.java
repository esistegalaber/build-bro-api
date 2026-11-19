package com.egalaber.buildbro.api.model;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedMap;
import java.util.TreeMap;

/**
 * Defensive, null-safe copies for record components, so API models stay immutable
 * and a collection missing from incoming JSON becomes empty instead of {@code null}.
 */
final class ModelCollections {

    private ModelCollections() {
    }

    static <T> List<T> list(List<T> source) {
        return source == null ? List.of() : List.copyOf(source);
    }

    /** Keeps insertion order, so JSON output stays stable. */
    static <K, V> Map<K, V> map(Map<K, V> source) {
        return source == null ? Map.of() : Collections.unmodifiableMap(new LinkedHashMap<>(source));
    }

    static <K extends Comparable<K>, V> SortedMap<K, V> sortedMap(Map<K, V> source) {
        return Collections.unmodifiableSortedMap(source == null ? new TreeMap<>() : new TreeMap<>(source));
    }
}
