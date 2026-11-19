package com.egalaber.buildbro.api

import groovy.json.JsonOutput
import groovy.json.JsonSlurper

import java.nio.file.Files
import java.nio.file.Path

/**
 * Writes the OpenAPI document of the running app to {@code target/openapi/openapi.json} and {@code openapi.yaml}
 * (the build renders static HTML docs from it during {@code package}), and checks that every operation is documented.
 */
class OpenApiDocsSpec extends BaseRestSpec {

    private static final Path OUTPUT_DIR = Path.of(System.getProperty('openapi.output.dir', 'target/openapi'))

    def "Writes the OpenAPI document"() {
        when:
        String json = restTemplate.getForObject("http://localhost:${port}/v3/api-docs", String)
        String yaml = restTemplate.getForObject("http://localhost:${port}/v3/api-docs.yaml", String)
        Files.createDirectories(OUTPUT_DIR)
        Files.writeString(OUTPUT_DIR.resolve('openapi.json'), JsonOutput.prettyPrint(json))
        Files.writeString(OUTPUT_DIR.resolve('openapi.yaml'), yaml)

        then:
        yaml.startsWith('openapi:')
        Map doc = new JsonSlurper().parseText(json) as Map
        doc.info.title == 'Build Bro API'
    }

    def "Every operation has a summary, a tag and a readable operationId"() {
        given:
        Map doc = new JsonSlurper().parseText(restTemplate.getForObject("http://localhost:${port}/v3/api-docs", String)) as Map
        List<Map> operations = doc.paths.values().collectMany { Map ops -> ops.values() as List<Map> }

        expect:
        operations.size() == 27
        operations.findAll { !it.summary }*.operationId == []
        operations.findAll { !it.tags || it.tags.any { String t -> t.endsWith('-impl') } }*.operationId == []

        and: 'explicit operationIds: springdoc numbers clashing method names (get1, search1, ...)'
        operations*.operationId.findAll { String id -> id ==~ /.*\d/ } == []
    }

    def "Error responses come from the throws clauses"() {
        given:
        Map paths = (new JsonSlurper().parseText(restTemplate.getForObject("http://localhost:${port}/v3/api-docs", String)) as Map).paths

        expect:
        paths['/api/v1/builds/{buildId}'].get.responses.keySet() == ['200', '404'] as Set
        paths['/api/v1/builds/create'].post.responses.keySet() == ['200', '400'] as Set
        paths['/api/v1/deployments'].post.responses.keySet() == ['200'] as Set
        paths['/api/v1/builds/{buildId}'].get.responses['404'].content['application/json'].schema.'$ref' ==
                '#/components/schemas/IExceptionInfo'
    }
}
