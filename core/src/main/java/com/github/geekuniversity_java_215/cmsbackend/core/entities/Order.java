package com.github.geekuniversity_java_215.cmsbackend.core.entities;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.OrderStatus;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "delivery_order")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Order extends AbstractEntity {

    @NotNull
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @NotNull
    @ManyToOne
    @JoinColumn(name="courier_id")
    private Courier courier;

    @Transient
    private OrderStatus status;

    // Enum mapping
    @Basic
    private int statusValue;

    @PostLoad
    void fillCurrencyCode() {
        if (statusValue > 0) {
            this.status = OrderStatus.idOf(statusValue);
        }
    }

    @PrePersist
    void fillCurrencyCodeValue() {
        if (status != null) {
            this.statusValue = status.getId();
        }
    }

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private Address from;

    @OneToOne(cascade = CascadeType.ALL, orphanRemoval = true)
    @JoinColumn
    private Address to;

//    protected void setId(Long id) {
//        this.id = id;
//    }


    @Override
    public String toString() {
        return "Order{" +
               "id=" + id +
               ", customer=" + customer +
               ", courier=" + courier +
               ", status=" + status +
               '}';
    }
}