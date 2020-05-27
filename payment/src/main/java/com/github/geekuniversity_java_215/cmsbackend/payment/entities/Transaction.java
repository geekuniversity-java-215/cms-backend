package com.github.geekuniversity_java_215.cmsbackend.payment.entities;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;
import java.util.Calendar;

@Entity
@Table(name = "request_for_funds")
@Data
@EqualsAndHashCode(callSuper=true)
public class Transaction extends AbstractEntity {

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @NotNull
    private String typeOperation;

    @NotNull
    private BigDecimal amount;

    @NotNull
    private String currencyCodeType;

    @Email
    @NotBlank
    private String payPalEmail;

    @NotNull
    private Date dateCreate;

    @NotNull
    private Date dateSuccess;

    public Transaction(){
    }

    public Transaction(User user, String typeOperation, BigDecimal amount, String payPalEmail, String currencyCodeType) {
        this.user=user;
        this.typeOperation=typeOperation;
        this.amount=amount;
        this.payPalEmail = payPalEmail;
        this.currencyCodeType=currencyCodeType;
        this.dateCreate=new java.sql.Date(Calendar.getInstance().getTime().getTime());
        this.dateSuccess=java.sql.Date.valueOf("4000-01-01");
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