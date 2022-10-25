package com.egalaber.buildbro.api.model;

import java.io.Serializable;
import java.util.Objects;

public class IBuildLabel implements Serializable, Comparable<IBuildLabel> {
    private Long id;
    private String key;
    private String value;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @Override
    public int compareTo(IBuildLabel other) {
        return getKey().compareTo(other.getKey());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IBuildLabel that = (IBuildLabel) o;
        return Objects.equals(getId(), that.getId()) && Objects.equals(getKey(), that.getKey()) && Objects.equals(getValue(), that.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getKey(), getValue());
    }

    @Override
    public String toString() {
        return "IBuildLabel{" +
                "id=" + id +
                ", name='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
