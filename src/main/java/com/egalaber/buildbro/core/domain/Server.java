package com.egalaber.buildbro.core.domain;


import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
        name = "server",
        indexes = {
                @Index(name = "unq_server_name", columnList = "name", unique = true)
        }
)
public class Server implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Basic(optional = false)
    @Column(name = "name", nullable = false)
    private String name;
    @Basic()
    @Column(name = "nick_name")
    private String nickName;
    @Basic()
    @Column(name = "description")
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
        Server server = (Server) o;
        return Objects.equals(getName(), server.getName()) && Objects.equals(getNickName(), server.getNickName()) && Objects.equals(getDescription(), server.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getNickName(), getDescription());
    }
}
