package com.egalaber.buildbro.core.repository;

/**
 * How many builds or deployments carry a label key/value pair. Values that differ only in case are counted
 * together (the columns' collation is case-insensitive).
 */
public record LabelUsage(String key, String value, Long count) {
}
