package com.github.geekuniversity_java_215.cmsbackend.core.entities.CurrencyConverter.pogo;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;


@XmlRootElement(name = "ValCurs")
public class ValCurs {

    @XmlAttribute(name = "Date")
    public String date;

    @XmlAttribute(name = "name")
    public String name;

    @XmlElement(name = "Valute")
    public ArrayList<Valute> valutes;

}