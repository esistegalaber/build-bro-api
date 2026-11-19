package com.egalaber.buildbro.api.model

import org.codehaus.groovy.runtime.typehandling.DefaultTypeTransformation

import java.lang.reflect.RecordComponent

/**
 * Named-argument factories for the API model records, so specs can keep writing
 * {@code aBuild(project: 'backend', buildNumber: 1)} instead of long positional constructor calls.
 * Omitted components are passed as {@code null} (the records turn null collections into empty ones).
 * Unknown keys fail fast, so a typo can't silently produce a half-empty model.
 *
 * Use with {@code import static com.egalaber.buildbro.api.model.ApiModels.*}
 */
final class ApiModels {

    private ApiModels() {
    }

    static IBuild aBuild(Map args = [:]) { create(IBuild, args) }

    static IBuildLabel aBuildLabel(Map args = [:]) { create(IBuildLabel, args) }

    static IBuildNumber aBuildNumber(Map args = [:]) { create(IBuildNumber, args) }

    static IBuildTemplate aBuildTemplate(Map args = [:]) { create(IBuildTemplate, args) }

    static IBuildSetTemplate aBuildSetTemplate(Map args = [:]) { create(IBuildSetTemplate, args) }

    static IBuildSearch aBuildSearch(Map args = [:]) { create(IBuildSearch, args) }

    static IDeployment aDeployment(Map args = [:]) { create(IDeployment, args) }

    static IDeploymentLabel aDeploymentLabel(Map args = [:]) { create(IDeploymentLabel, args) }

    static IDeploymentSearch aDeploymentSearch(Map args = [:]) { create(IDeploymentSearch, args) }

    static IServer aServer(Map args = [:]) { create(IServer, args) }

    /** Calls the canonical constructor of {@code type}, taking each component from {@code args} by name. */
    static <T extends Record> T create(Class<T> type, Map args) {
        RecordComponent[] components = type.recordComponents
        Set unknown = args.keySet() - components*.name
        if (unknown) {
            throw new IllegalArgumentException("${type.simpleName} has no component(s) ${unknown}; known: ${components*.name}")
        }
        Object[] values = components.collect { RecordComponent c ->
            // e.g. Groovy's Integer literal 1 for a Long component
            DefaultTypeTransformation.castToType(args[c.name], c.type)
        } as Object[]
        type.getDeclaredConstructor(components*.type as Class[]).newInstance(values)
    }
}
