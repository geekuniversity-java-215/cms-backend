package com.github.geekuniversity_java_215.cmsbackend.entites;

import com.github.geekuniversity_java_215.cmsbackend.aop.LogExecutionTime;
import com.github.geekuniversity_java_215.cmsbackend.entites.base.AbstractEntity;
import com.github.geekuniversity_java_215.cmsbackend.entites.base.Person;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;


/**
 * Счет
 */
@Entity
@Table(name = "account")
@Data
@EqualsAndHashCode(callSuper=true)
@NoArgsConstructor
public class Account extends AbstractEntity {

    @Id
    @Column(name = "id")
    @GeneratedValue(generator = "account_id_seq")
    protected Long id;

    @NotNull
    @Column(name = "balance")
    private BigDecimal balance = new BigDecimal(0);

    @NotNull
    @OneToOne(mappedBy = "account")
    private Person person;

    @LogExecutionTime
    public BigDecimal getBalance() {
        return balance;
    }


    protected void setId(Long id) {
        this.id = id;
    }

    /**
     * Не трогать этот setter руками/через reflection(Utils.fieldSetter(...))!<br>
     * Поле balance на запись управляется только через AccountService
     * @param balance
     */
    protected void setBalance(BigDecimal balance) {
        this.balance = balance;
    }


    @Override
    public String toString() {
        return "Account{" +
               "balance=" + balance +
               '}';
    }
}
