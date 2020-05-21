package com.github.geekuniversity_java_215.cmsbackend.core.entities;


import com.github.geekuniversity_java_215.cmsbackend.core.entities.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.sql.Date;

@Entity
@Table(name = "request_for_funds")
@Data
@EqualsAndHashCode(callSuper=true)
public class RequestForFunds extends AbstractEntity {

    @NotNull
    @OneToOne
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

    @NotNull
    private Date dateSuccess;

    protected RequestForFunds() {}

    @Override
    public String toString() {
        return "Client{" +
                "id=" + id +
                ", user=" + user +
                '}';
    }
}