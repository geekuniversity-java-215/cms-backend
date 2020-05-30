package com.github.geekuniversity_java_215.cmsbackend.core.entities;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "orrder")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Order extends AbstractEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name="client_id")
    private Client client;

    @ManyToOne
    @JoinColumn(name="courier_id")
    private Courier courier;

    @Transient
    private OrderStatus status;

    //region Transient enum mapping OrderStatus
    // Enum mapping
    @Basic
    private int statusValue;

    @PostLoad
    void fillCurrencyCode() {
        this.status = OrderStatus.getById(statusValue);
    }

    @PrePersist
    void fillCurrencyCodeValue() {
        if (status != null) {
            this.statusValue = status.getId();
        }
    }
    //endregion

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="from_id")
    private Address from;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn(name="to_id")
    private Address to;

//    protected void setId(Long id) {
//        this.id = id;
//    }


    @Override
    public String toString() {
        return "Order{" +
            "id=" + id +
            ", client=" + client +
            ", courier=" + courier +
            ", status=" + status +
            '}';
    }
}