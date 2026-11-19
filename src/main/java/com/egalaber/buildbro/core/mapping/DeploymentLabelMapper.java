package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IDeploymentLabel;
import com.egalaber.buildbro.core.domain.DeploymentLabel;

public class DeploymentLabelMapper {
    private DeploymentLabelMapper() {
    }

    public static IDeploymentLabel toApi(DeploymentLabel deploymentLabel) {
        return new IDeploymentLabel(deploymentLabel.getId(), deploymentLabel.getKey(), deploymentLabel.getValue());
    }
}
