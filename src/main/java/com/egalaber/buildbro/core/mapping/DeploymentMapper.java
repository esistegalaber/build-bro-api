package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IDeployment;
import com.egalaber.buildbro.core.domain.Deployment;

public class DeploymentMapper {
    private DeploymentMapper() {
    }

    public static IDeployment toApi(Deployment deployment) {
        return new IDeployment(
                deployment.getId(),
                deployment.getServer().getName(),
                deployment.getDeployedAt(),
                deployment.getBuilds().stream().map(BuildMapper::toApi).toList(),
                deployment.getLabels().stream().map(DeploymentLabelMapper::toApi).toList()
        );
    }
}
