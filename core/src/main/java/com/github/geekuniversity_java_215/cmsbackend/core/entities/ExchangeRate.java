package com.github.geekuniversity_java_215.cmsbackend.core.entities;

import javax.persistence.*;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import lombok.Data;

import java.util.Objects;

@Entity
@Table(name = "exchange_rate")
@Data
public class ExchangeRate extends AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "exchange_rate_id_seq")
    private Integer id;

    @Column(name = "cbr_id", unique = true)
    private String cbrId;

    @Basic
    private int currencyCodeValue;

    @Transient
    private CurrencyCode currencyCode;

    @Column
    private Integer nominal;

    // name - уже есть в CurrencyCode?
    //@Column
    //private String name;

    @PostLoad
    void fillCurrencyCode() {
        if (currencyCodeValue > 0) {
            this.currencyCode = CurrencyCode.codeOf(currencyCodeValue);
        }
    }

    @PrePersist
    void fillCurrencyCodeValue() {
        if (currencyCode != null) {
            this.currencyCodeValue = currencyCode.getCode();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ExchangeRate that = (ExchangeRate) o;
        return id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
