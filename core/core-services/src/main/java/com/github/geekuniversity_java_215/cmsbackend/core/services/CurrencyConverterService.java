package com.github.geekuniversity_java_215.cmsbackend.core.services;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CurrencyConverterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@Transactional
public class CurrencyConverterService {

    private CurrencyConverterRepository currencyConverterRepository;

    @Autowired
    public CurrencyConverterService(CurrencyConverterRepository currencyConverterRepository) {
        this.currencyConverterRepository = currencyConverterRepository;
    }

    public BigDecimal convertCurrency(BigDecimal amount, CurrencyCode currencyCode){

        if (CurrencyCode.RUB.equals(currencyCode)){
            return amount;
        }

        // TODO: 28.04.2020 convert to real course
        //EUR
        if (CurrencyCode.EUR.equals(currencyCode)){
            return amount.multiply(new BigDecimal(80));
        }

        //USD
        return amount.multiply(new BigDecimal(75));

    }


}