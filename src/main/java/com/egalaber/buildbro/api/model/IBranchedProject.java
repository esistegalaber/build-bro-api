package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public class IBranchedProject implements Serializable {
    String project;
    String branch;

    public String getProject() {
        return project;
    }

    public void setProject(String project) {
        this.project = project;
    }

    public String getBranch() {
        return branch;
    }

    public void setBranch(String branch) {
        this.branch = branch;
    }
}
