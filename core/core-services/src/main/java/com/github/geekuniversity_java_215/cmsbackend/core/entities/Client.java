package com.github.geekuniversity_java_215.cmsbackend.core.entities;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
    name = "client",
    indexes = {@Index(name = "client_user_id_unq", columnList = "user_id", unique = true)
    })
@Data
public class Client extends AbstractEntity {

    @NotNull
    @OneToOne
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy= "client", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    protected List<Order> orderList = new ArrayList<>();

    // stub
    private String clientSpecificData;

    public Client() {}

    public Client(@NotNull User user, String clientSpecificData) {
        this.user = user;
        user.setClient(this);
        this.clientSpecificData = clientSpecificData;
    }

    public void setUser(User user) {
        this.user = user;
        if (user.getClient() != this) {
            user.setClient(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Client)) return false;
        Client client = (Client) o;
        return user.equals(client.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "Client{" +
            "id=" + id +
            ", user=" + user +
            '}';
    }
}