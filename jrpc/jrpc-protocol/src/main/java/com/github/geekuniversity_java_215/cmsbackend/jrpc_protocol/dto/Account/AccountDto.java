package com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.Account;

import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto._base.AbstractDto;
import com.github.geekuniversity_java_215.cmsbackend.jrpc_protocol.dto.user.UserDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
public class AccountDto extends AbstractDto {

    private BigDecimal balance = new BigDecimal(0);
    private UserDto user;

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public String toString() {
        return "AccountDto{" +
            "id=" + id +
            ", balance=" + balance +
            '}';
    }
}
