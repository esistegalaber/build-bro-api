package com.egalaber.buildbro.api

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.servlet.mvc.method.RequestMappingInfo
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping
import org.yaml.snakeyaml.Yaml

/**
 * The contract ({@code openapi/build-bro-api.yaml}) is the source of the API surface: it is served as the API docs,
 * and the running app implements exactly the operations it describes.
 */
class ContractSpec extends BaseRestSpec {

    private static final String CONTRACT = '/openapi/build-bro-api.yaml'

    @Autowired
    @Qualifier('requestMappingHandlerMapping')
    RequestMappingHandlerMapping handlerMapping

    def "Serves the contract unchanged"() {
        when:
        ResponseEntity<String> response = restTemplate.getForEntity("http://localhost:${port}${CONTRACT}", String)

        then:
        response.statusCode == HttpStatus.OK
        response.body == contractText()
    }

    def "Swagger UI shows the contract"() {
        when:
        ResponseEntity<String> ui = restTemplate.getForEntity("http://localhost:${port}/swagger-ui/index.html", String)

        then:
        ui.statusCode == HttpStatus.OK
        ui.body.contains("url: '..${CONTRACT}'")

        and: 'the page loads Swagger UI from the webjar, without a version in the URL'
        ['swagger-ui.css', 'swagger-ui-bundle.js'].every { String file ->
            restTemplate.getForEntity("http://localhost:${port}/webjars/swagger-ui/${file}", String).statusCode == HttpStatus.OK
        }
    }

    def "No spec is generated from the code any more"() {
        expect:
        restTemplate.getForEntity("http://localhost:${port}/v3/api-docs", String).statusCode == HttpStatus.NOT_FOUND
    }

    def "The app implements exactly the operations of the contract"() {
        given: 'METHOD /path of every operation in the contract'
        Map contract = new Yaml().load(contractText()) as Map
        Set<String> inContract = contract.paths.collectMany { String path, Map operations ->
            operations.keySet().collect { String method -> "${method.toUpperCase()} ${path}".toString() }
        } as Set

        and: 'METHOD /path of every /api handler of the running app'
        Set<String> implemented = handlerMapping.handlerMethods.keySet().collectMany { RequestMappingInfo info ->
            info.pathPatternsCondition.patternValues.findAll { it.startsWith('/api/') }.collectMany { String path ->
                info.methodsCondition.methods.collect { "${it} ${path}".toString() }
            }
        } as Set

        expect: 'a generated interface without a @RestController would show up as missing'
        inContract.size() == 34
        (inContract - implemented) == [] as Set
        (implemented - inContract) == [] as Set
    }

    private static String contractText() {
        ContractSpec.getResource(CONTRACT).text
    }
}
