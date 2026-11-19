package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public record IExceptionInfo(String key, String description, String message) implements Serializable {
}
