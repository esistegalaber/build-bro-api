package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IDeploymentLabel;
import com.egalaber.buildbro.core.domain.DeploymentLabel;

public class DeploymentLabelMapper {
    private DeploymentLabelMapper() {
    }

    public static IDeploymentLabel toApi(DeploymentLabel deploymentLabel) {
        IDeploymentLabel toReturn = new IDeploymentLabel();
        toReturn.setId(deploymentLabel.getId());
        toReturn.setKey(deploymentLabel.getKey());
        toReturn.setValue(deploymentLabel.getValue());
        return toReturn;
    }
}
