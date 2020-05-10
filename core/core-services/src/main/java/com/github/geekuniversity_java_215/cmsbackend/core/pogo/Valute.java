package com.github.geekuniversity_java_215.cmsbackend.core.entities.CurrencyConverter.pogo;

import lombok.*;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import java.math.BigDecimal;

@ToString
@EqualsAndHashCode
public class Valute {
    @XmlAttribute(name = "ID")
    private String id;

    @XmlElement(name = "Name")
    private String name;

    @XmlElement(name = "Value")
    private String value;

    @XmlElement(name = "Nominal")
    private String nominal;

    @XmlElement(name = "CharCode")
    private String charCode;

    @XmlElement(name = "NumCode")
    private String numCode;

    private BigDecimal valueB;

    public int getNumCode() {
        return Integer.parseInt(numCode);
    }

    public BigDecimal getValue(){
        return new BigDecimal(value.replace(",", "."));
    }

    public String getName() {
        return name;
    }

}
