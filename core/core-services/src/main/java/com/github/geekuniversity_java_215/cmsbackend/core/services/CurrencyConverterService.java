package com.github.geekuniversity_java_215.cmsbackend.core.services;

import com.github.geekuniversity_java_215.cmsbackend.core.data.currencyconverter.Valute;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.ExchangeRate;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CurrencyConverterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.sql.Date;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Optional;

@Service
@Transactional
public class CurrencyConverterService {

    private final CurrencyConverterRepository currencyConverterRepository;
    private final CurrencyImportService currencyImportService;

    @Autowired
    public CurrencyConverterService(CurrencyConverterRepository currencyConverterRepository,
                                    CurrencyImportService currencyImportService) {
        this.currencyConverterRepository = currencyConverterRepository;
        this.currencyImportService = currencyImportService;
    }

    public BigDecimal convertCurrency(BigDecimal amount, CurrencyCode currencyCode){

        if (CurrencyCode.RUB.equals(currencyCode)){
            return amount;
        }

        Date date = new Date(System.currentTimeMillis());

        Optional<ExchangeRate> value =
                currencyConverterRepository.findOneByCurrencyCodeAndDate(currencyCode, date);

        if(!value.isPresent()) {

            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            gregorianCalendar.setTime(date);

            value = currencyImportService.importCurrencies(gregorianCalendar, currencyCode);

        }

        return value.get().getValue().multiply(amount);

    }

    private BigDecimal getRate(CurrencyCode currencyCode, GregorianCalendar calendar){

        Optional<ExchangeRate> exchangeRate = currencyConverterRepository.findOneByCurrencyCodeAndDate(currencyCode,
                new Date(calendar.getTimeInMillis()));

        return exchangeRate.get().getValue();

    }


}