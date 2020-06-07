package com.github.geekuniversity_java_215.cmsbackend.payment.entities;


import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.utils.data.enums.OrderStatus;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.Instant;

@Entity
@Table(name = "cashflow")
@Data
@EqualsAndHashCode(callSuper=true)
public class CashFlow extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @NotNull
    private String typeOperation;

    @NotNull
    private BigDecimal amount;

    private CurrencyCode currencyCode;

    @Email
    @NotBlank
    private String payPalEmail;

    private Instant dateSuccess;


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

    public CashFlow() {
    }

    public CashFlow(User user, String typeOperation, BigDecimal amount, String payPalEmail, CurrencyCode currencyCode) {
        this.user = user;
        this.typeOperation = typeOperation;
        this.amount = amount;
        this.payPalEmail = payPalEmail;
        this.currencyCode = currencyCode;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", amount=" + amount +
                ", payPalEmail=" + payPalEmail +
                ", typeOperation=" + typeOperation+
                '}';
    }

}
