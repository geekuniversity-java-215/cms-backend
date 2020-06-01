package com.github.geekuniversity_java_215.cmsbackend.payment.entities;


import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
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

    @NotNull
    private CurrencyCode currencyCodeType;

    @Email
    @NotBlank
    private String payPalEmail;

    private Instant dateSuccess;

    public CashFlow(){
    }

    public CashFlow(User user, String typeOperation, BigDecimal amount, String payPalEmail, CurrencyCode currencyCodeType) {
        this.user=user;
        this.typeOperation=typeOperation;
        this.amount=amount;
        this.payPalEmail = payPalEmail;
        this.currencyCodeType=currencyCodeType;
    }

    public CashFlow(User user, String typeOperation, BigDecimal amount, String payPalEmail, String currencyCodeType) {
        this.user=user;
        this.typeOperation=typeOperation;
        this.amount=amount;
        this.payPalEmail = payPalEmail;
        this.currencyCodeType= CurrencyCode.valueOf(currencyCodeType);
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