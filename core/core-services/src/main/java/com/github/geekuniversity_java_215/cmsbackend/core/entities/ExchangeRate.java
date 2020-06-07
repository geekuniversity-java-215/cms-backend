package com.github.geekuniversity_java_215.cmsbackend.core.entities;

import javax.persistence.*;

import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "exchange_rate")
@Data
@EqualsAndHashCode(callSuper = true)
public class ExchangeRate extends AbstractEntity {

    //@Transient - нельзя иначе хибер скажет что изменения только в этом поле
    // не меняют сущность и не будет вызывать @PreUpdate
    // - мусорное поле в базе
    @Column(name = "currency_code")
    private CurrencyCode currencyCode;

    @Column(name = "date")
    private Date date;

    @Column(name = "value")
    private BigDecimal value;

    //region enum mapping CurrencyCode
    @Basic
    private int currencyCodeValue;

    @PostLoad
    void fillCurrencyCode() {
        if (currencyCodeValue > 0) {
            this.currencyCode = CurrencyCode.codeOf(currencyCodeValue);
        }
    }

    @PrePersist
    @PreUpdate
    void fillCurrencyCodeValue() {
        if (currencyCode != null) {
            this.currencyCodeValue = currencyCode.getCode();
        }
    }
    //endregion
}
