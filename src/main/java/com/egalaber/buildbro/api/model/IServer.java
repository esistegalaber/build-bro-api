package com.egalaber.buildbro.api.model;

import java.io.Serializable;

public record IServer(Long id, String name, String nickName, String description) implements Serializable {
}
