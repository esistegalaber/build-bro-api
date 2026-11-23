package com.egalaber.buildbro.core.repository;

/** How many builds or deployments carry a label key, whatever its value. */
public record LabelKeyUsage(String key, Long count) {
}
