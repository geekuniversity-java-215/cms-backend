package com.github.geekuniversity_java_215.cmsbackend.payment.services;

import com.github.geekuniversity_java_215.cmsbackend.core.services.base.BaseRepoAccessService;
import com.github.geekuniversity_java_215.cmsbackend.payment.entities.CashFlow;
import com.github.geekuniversity_java_215.cmsbackend.payment.repository.CashFlowRepository;
import com.github.geekuniversity_java_215.cmsbackend.utils.StringUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    public CashFlow save(CashFlow cashFlow) {

        if (StringUtils.isBlank(cashFlow.getUser().getPayPalEmail())) {
            cashFlow.getUser().setPayPalEmail(cashFlow.getPayPalEmail());
        }
        return super.save(cashFlow);
    }

    //
//    public CashFlow addCashFlow(User user, BigDecimal amount, String payPalEmail, CurrencyCode currencyCodeType){
//
//        CashFlow result;
//        result = new CashFlow(user, REQUEST, amount, payPalEmail, currencyCodeType);
//        result = cashFlowRepository.save(result);
//        checkPayPalEmail(payPalEmail, user);
//        log.info("id transaction = "+ result.getId());
//        return result;
//    }


//    public List<CashFlow> findAll(CashFlowSpecDto specDto) {
//
//        Specification<CashFlow> spec =  CashFlowSpecBuilder.build(specDto);
//        return cashFlowRepository.findAll(spec);
//    }

//    public List<CashFlow> findAllNoSuccess() {
//        return cashFlowRepository.findAllNoSuccess();
//    }

    // =====================================================================================


}
