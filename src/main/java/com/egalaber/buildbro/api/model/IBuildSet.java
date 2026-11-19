package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.Map;

/**
 * The concrete builds a build-set template resolves to, keyed (and sorted) by project name.
 */
public record IBuildSet(String name, Map<String, IBuild> builds) implements Serializable {

    public IBuildSet {
        builds = ModelCollections.sortedMap(builds);
    }
}
