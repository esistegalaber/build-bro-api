package com.egalaber.buildbro.core.domain;


import javax.persistence.*;

@Entity
@Table(
        name = "server",
        indexes = {
                @Index(name = "unq_server_name", columnList = "name", unique = true)
        }
)
public class Server {
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
}
