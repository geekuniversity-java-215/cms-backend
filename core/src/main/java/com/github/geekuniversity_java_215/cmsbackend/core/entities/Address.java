package com.github.geekuniversity_java_215.cmsbackend.core.entities;

import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import lombok.*;

import javax.persistence.*;


@Entity
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@Table(name = "address")
public class Address extends AbstractEntity {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

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

        // TODO: geodata
//        if(building == 0){
//            return  "+" + city + "+" + street + "+" + house;
//        }
//        return "+" + city + "+" + street + "+" + house + "+" + building;
        String result = "+" + city + "+" + street + "+" + house;
        if(building != 0) {
            result +=  "+" + house;
        }
        return result;
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
