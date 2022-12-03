package com.egalaber.buildbro.core.service

import com.egalaber.buildbro.BaseBuildBroSpec
import com.egalaber.buildbro.api.model.IProject
import org.springframework.beans.factory.annotation.Autowired
import spock.lang.Subject

class ProjectServiceTest extends BaseBuildBroSpec {
    @Subject
    @Autowired
    ProjectService projectService

    def "ProjectData with only active projects"() {
        when: 'get project data'
        List<IProject> data = projectService.projectData(false)

        then: 'data list of projects returned'
        !data.isEmpty()

        and: 'but only active ones'
        data.every { project ->
            project.active
        }
    }

    def "ProjectData with including inactive projects"() {
        when: 'get project data'
        List<IProject> data = projectService.projectData(true)

        then: 'data list of projects returned'
        !data.isEmpty()

        and: 'including the inactive one'
        data.find { project ->
            project.active == false
        } != null
    }
}
