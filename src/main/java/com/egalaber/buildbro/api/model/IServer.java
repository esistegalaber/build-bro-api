package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.Objects;

public class IServer implements Serializable {
    private Long id;
    private String name;
    private String nickName;
    private String description;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IServer iServer = (IServer) o;
        return Objects.equals(getId(), iServer.getId()) && Objects.equals(getName(), iServer.getName()) && Objects.equals(getNickName(), iServer.getNickName()) && Objects.equals(getDescription(), iServer.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getName(), getNickName(), getDescription());
    }
}
