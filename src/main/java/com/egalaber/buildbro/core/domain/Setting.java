package com.egalaber.buildbro.core.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;

/** An instance-wide setting, stored as a key/value pair. */
@Entity
@Table(name = "setting")
@Getter
@Setter
public class Setting implements Serializable {
    @Id
    @Column(name = "setting_key", nullable = false)
    private String key;
    @Basic(optional = false)
    @Column(name = "setting_value", nullable = false)
    private String value;

    public Setting() {
    }

    public Setting(String key, String value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Setting setting = (Setting) o;
        return Objects.equals(getKey(), setting.getKey()) && Objects.equals(getValue(), setting.getValue());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getKey(), getValue());
    }

    @Override
    public String toString() {
        return "Setting{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
