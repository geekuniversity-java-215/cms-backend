package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
import com.github.geekuniversity_java_215.cmsbackend.core.services.UserService;
import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import com.github.geekuniversity_java_215.cmsbackend.payment.repository.CashFlowRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@Transactional
public class CashFlowService extends BaseRepoAccessService<CashFlow> {
    private final static String REQUEST="request";
    private final static String DEPOSIT="deposit";

    private final CashFlowRepository cashFlowRepository;
    private final UserService userService;

    @Autowired
    public CashFlowService(CashFlowRepository cashFlowRepository, UserService userService){
        super(cashFlowRepository);
        this.cashFlowRepository = cashFlowRepository;
        this.userService = userService;
    }

    public CashFlow addRequestForFunds(Long userId, BigDecimal amount, String payPalEmail, CurrencyCode currencyCodeType){
        Optional<User> user=userService.findById(userId);
        CashFlow cashFlow =new CashFlow(user.get(),REQUEST,amount,payPalEmail,currencyCodeType);
        cashFlowRepository.save(cashFlow);
        checkPayPalEmail(payPalEmail, user);
        log.info("id transaction = "+ cashFlow.getId());
        return cashFlow;
    }

    private void checkPayPalEmail(String payPalEmail, Optional<User> user) {
        if (user.get().getPayPalEmail()== null){
            user.get().setPayPalEmail(payPalEmail);
        }
    }

    public List<CashFlow> findAllWithEmptyDateSuccess() {
        return cashFlowRepository.findAllWithEmptyDateSuccess();
    }

    /*
    who knows why this method is needed?
     */
    public List<Date> findAllDateSuccess() {
        return cashFlowRepository.findAllDateSuccess();
    }
}
