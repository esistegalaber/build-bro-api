package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBranch;
import com.egalaber.buildbro.core.domain.Branch;

public class BranchMapper {
    private BranchMapper() {
    }

    public static IBranch toApi(Branch branch) {
        IBranch toReturn = new IBranch();
        toReturn.setId(branch.getId());
        toReturn.setName(branch.getName());
        toReturn.setActive(branch.getActive());
        toReturn.setProjectId(branch.getProject().getId());
        return toReturn;
    }
}
