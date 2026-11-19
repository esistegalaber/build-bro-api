package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public record IBranchedProject(String project, String branch) implements Serializable {
}
