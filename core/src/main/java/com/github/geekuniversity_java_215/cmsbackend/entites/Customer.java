package com.github.geekuniversity_java_215.cmsbackend.entites;


import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table(name = "customer")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Customer extends Person {

}