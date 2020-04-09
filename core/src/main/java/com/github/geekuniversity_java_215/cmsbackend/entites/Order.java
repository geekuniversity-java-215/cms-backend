package com.github.geekuniversity_java_215.cmsbackend.entites;

import javax.persistence.*;

@Entity
@Table(name = "delivery_order")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long Id;

    @OneToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;

    @OneToOne
    @JoinColumn(name = "courier_id")
    private Сourier courier;

    public Order (){

    }

    public Long getId() {
        return Id;
    }

    public void setId(Long id) {
        Id = id;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Сourier getCourier() {
        return courier;
    }

    public void setCourier(Сourier courier) {
        this.courier = courier;
    }
}