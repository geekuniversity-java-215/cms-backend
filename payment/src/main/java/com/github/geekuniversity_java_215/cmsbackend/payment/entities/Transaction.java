package com.github.geekuniversity_java_215.cmsbackend.payment.entities;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
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

    @NotNull
    private Date dateCreate;

    private Date dateSuccess;

    public Transaction(){
    }

    public Transaction(User user, String typeOperation, BigDecimal amount, String currencyCodeType) {
        this.user=user;
        this.typeOperation=typeOperation;
        this.amount=amount;
        this.currencyCodeType=currencyCodeType;
        this.dateCreate=new java.sql.Date(Calendar.getInstance().getTime().getTime());// (Date) new java.util.Date();

        //временная заглушка. dateSuccess должен заполняться, когда транзакция отработана
        this.dateSuccess=new java.sql.Date(Calendar.getInstance().getTime().getTime());// (Date) new java.util.Date();
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", userId=" + user.getId() +
                ", amount=" + amount +
                ", typeOperation=" + typeOperation+
                '}';
    }

}