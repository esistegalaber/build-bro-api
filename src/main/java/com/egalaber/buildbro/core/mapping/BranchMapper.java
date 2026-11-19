package com.egalaber.buildbro.core.mapping;

import com.egalaber.buildbro.api.model.IBranch;
import com.egalaber.buildbro.core.domain.Branch;

public class BranchMapper {
    private BranchMapper() {
    }

    public static IBranch toApi(Branch branch) {
        return new IBranch(branch.getId(), branch.getName(), branch.getActive(), branch.getProject().getId());
    }
}
