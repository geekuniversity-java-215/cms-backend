package com.github.geekuniversity_java_215.cmsbackend.core.entities;

import javax.persistence.*;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "exchange_rate")
@Data
public class ExchangeRate extends AbstractEntity {

    @Column(name = "currency_code")
    private CurrencyCode currencyCode;

    @Column(name = "date")
    private Date date;

    @Column(name = "value")
    private BigDecimal value;

    // Enum mapping
    @Basic
    private int currencyCodeValue;

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
}
