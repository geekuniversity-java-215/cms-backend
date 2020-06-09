package com.github.geekuniversity_java_215.cmsbackend.core.entities;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(
    name = "courier",
    indexes = {@Index(name = "courier_user_id_unq", columnList = "user_id", unique = true)
    })
@Data
public class Courier extends AbstractEntity {

    @NotNull
    @OneToOne    
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy= "courier", orphanRemoval = true, cascade = CascadeType.ALL)
    @OrderBy("id ASC")
    protected List<Order> orderList = new ArrayList<>();

    // stub
    private String courierSpecificData;


    public Courier() {}
    public Courier(@NotNull User user, String courierSpecificData) {
        this.user = user;
        user.setCourier(this);
        this.courierSpecificData = courierSpecificData;
    }

    public void setUser(User user) {
        this.user = user;
        if (user.getCourier() != this) {
            user.setCourier(this);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Courier)) return false;
        Courier courier = (Courier) o;
        return user.equals(courier.user);
    }

    @Override
    public int hashCode() {
        return Objects.hash(user);
    }

    @Override
    public String toString() {
        return "Courier{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}

// TODO: 09.04.2020 Добавить сущность автомобиль и подвязать к курьеру.
