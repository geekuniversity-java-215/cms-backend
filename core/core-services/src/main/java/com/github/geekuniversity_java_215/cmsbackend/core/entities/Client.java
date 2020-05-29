package com.github.geekuniversity_java_215.cmsbackend.core.entities;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "client")
@Data
@EqualsAndHashCode(callSuper=true)
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
        user.setClient(this);
    }

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}