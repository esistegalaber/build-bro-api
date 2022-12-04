package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IDeployment;
import com.egalaber.buildbro.core.domain.Deployment;

public class DeploymentMapper {
    private DeploymentMapper() {
    }

    public static IDeployment toApi(Deployment deployment) {
        IDeployment toReturn = new IDeployment();
        toReturn.setId(deployment.getId());
        toReturn.setServerName(deployment.getServer().getName());
        toReturn.setDeployedAt(deployment.getDeployedAt());
        toReturn.getBuilds().addAll(
                deployment.getBuilds().stream()
                        .map(BuildMapper::toApi)
                        .toList()
        );
        return toReturn;
    }
}
