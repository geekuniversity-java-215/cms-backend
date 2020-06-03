package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.user.User;
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

@Service
@Slf4j
@Transactional
public class CashFlowService extends BaseRepoAccessService<CashFlow> {
    private final static String REQUEST="request";
    private final static String DEPOSIT="deposit";

    private final CashFlowRepository cashFlowRepository;

    @Autowired
    public CashFlowService(CashFlowRepository cashFlowRepository){
        super(cashFlowRepository);
        this.cashFlowRepository = cashFlowRepository;
    }

    public CashFlow addRequestForFunds(User user, BigDecimal amount, String payPalEmail, CurrencyCode currencyCodeType){
        CashFlow cashFlow =new CashFlow(user,REQUEST,amount,payPalEmail,currencyCodeType);
        cashFlowRepository.save(cashFlow);
        checkPayPalEmail(payPalEmail, user);
        log.info("id cashflow = "+ cashFlow.getId());
        return cashFlow;
    }

    private void checkPayPalEmail(String payPalEmail, User user) {
        if (user.getPayPalEmail()== null){
            user.setPayPalEmail(payPalEmail);
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
