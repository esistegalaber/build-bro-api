package com.egalaber.buildbro.core.service;

import com.egalaber.buildbro.core.domain.BuildNumber;
import com.egalaber.buildbro.core.repository.BuildNumberRepository;
import com.egalaber.buildbro.core.repository.BuildNumberSpecs;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BuildNumberService {
    private final BuildNumberRepository buildNumberRepository;

    public BuildNumberService(BuildNumberRepository buildNumberRepository) {
        this.buildNumberRepository = buildNumberRepository;
    }

    public BuildNumber next(String project, String branch) {
        BuildNumber theCount = of(project, branch)
                .orElse(new BuildNumber(project, branch));
        theCount.increment();
        return buildNumberRepository.save(theCount);
    }

    public BuildNumber current(String project, String branch) {
        return of(project, branch).orElse(
                new BuildNumber(project, branch)
        );
    }

    public BuildNumber set(String project, String branch, Long buildCount) {
        BuildNumber theCount = of(project, branch)
                .orElse(
                        new BuildNumber(project, branch)
                );
        theCount.setNumber(buildCount);
        return buildNumberRepository.save(theCount);
    }

    private Optional<BuildNumber> of(@NonNull String project, @NonNull String branch) {
        return buildNumberRepository.findOne(
                BuildNumberSpecs.buildNumberOf(project, branch)
        );
    }
}
