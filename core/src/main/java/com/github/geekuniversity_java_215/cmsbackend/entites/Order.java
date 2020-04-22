package com.github.geekuniversity_java_215.cmsbackend.entites;

import com.github.geekuniversity_java_215.cmsbackend.entites.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "delivery_order")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Order extends AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "delivery_order_id_seq")
    protected Long id;

    @NotNull
    @ManyToOne
    @JoinColumn(name="customer_id")
    private Customer customer;

    @NotNull
    @ManyToOne
    @JoinColumn(name="courier_id")
    private Courier courier;

    @NotNull
    @Column(name = "status")
    private OrderStatus status;

    protected void setId(Long id) {
        this.id = id;
    }


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