package com.github.geekuniversity_java_215.cmsbackend.core.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "address")
public class Address {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String city;

    @Column
    private String street;

    @Column
    private int house;

    @Column
    private int building;

    @Column
    private int front_door;

    @Column
    private int flat;

    @Column
    private String longitude;

    @Column
    private String latitude;

    public Address(String city, String street, int house, int building, int flat) {
        this.city = city;
        this.street = street;
        this.house = house;
        this.building = building;
        this.flat = flat;
    }

    public String addressFormatToRequest(){
        if(building == 0){
            return  "+" + city + "+" + street + "+" + house;
        }
        return "+" + city + "+" + street + "+" + house + "+" + building;
    }

    @Override
    public String toString() {
        return "Address{city= " + city
                + ", street= " + street
                + ", house= " + house
                + ", building= " + building
                + ", front_door= " + front_door
                + ", flat= " + flat
                + ", longitude= " + longitude
                + ", latitude= " + latitude + "}";
    }
}
