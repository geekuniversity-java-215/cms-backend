package com.github.geekuniversity_java_215.cmsbackend.core.services;

import com.github.geekuniversity_java_215.cmsbackend.core.data.currencyconverter.ValCurs;
import com.github.geekuniversity_java_215.cmsbackend.core.data.currencyconverter.Valute;
import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.ExchangeRate;
import com.github.geekuniversity_java_215.cmsbackend.core.repositories.CurrencyConverterRepository;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.Nullable;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ExceptionHandler;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Objects;
import java.util.Optional;

// ToDo:
//  убрать предупреждения анализатора кода,
//  перенести CBR_URL в .properties

@Service
@Transactional
@Slf4j
@Component
public class CurrencyImportService {
    private static String CBR_URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";
   private final CurrencyConverterRepository currencyConverterRepository;

    @Autowired
    public CurrencyImportService (CurrencyConverterRepository currencyConverterRepository){
        this.currencyConverterRepository = currencyConverterRepository;
    }

    private String getUrl(GregorianCalendar date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setCalendar(date);
        String dateString = format.format(date.getTime());

        return CBR_URL + dateString;
    }

    @Nullable
    private ValCurs requestValCurs(GregorianCalendar date) {

        try {

            String urlString = getUrl(date);
            URL url = new URL(urlString);

            JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();

            return (ValCurs) jaxbUnmarshaller.unmarshal(url);
        } catch (Exception e) {
            // swallowing exception
            log.error("CurrencyImportService.requestValCurs failed:", e);
        }

        return null;

    }

    public void importCurrencies(GregorianCalendar date){

        Valute[] valutes = getValutefromCBR(date);
        saveAllCurrency(date, valutes);

    }

    public Optional<ExchangeRate> importCurrencies(GregorianCalendar date, CurrencyCode currencyCode){

        Valute[] valArray = getValutefromCBR(date);

        Valute valute = Arrays.stream(valArray).
                filter(e -> e.getNumCode() == currencyCode.getCode()).findFirst().
                orElseThrow(() -> new IllegalArgumentException("does not find selected course"));

        saveAllCurrency(date, valArray);

        ExchangeRate exRate = new ExchangeRate();
        exRate.setCurrencyCode(currencyCode);
        exRate.setDate(date.getTime());
        exRate.setValue(valute.getValue());

        return Optional.of(exRate);

    }

    private Valute[] getValutefromCBR(GregorianCalendar date) {

        ValCurs curs = requestValCurs(date);

        if (Objects.isNull(curs)) {
            throw new IllegalArgumentException("not course on date" + date);
        }

        return curs.valutes.toArray(new Valute[0]);

    }

    public void save(Valute valute, GregorianCalendar date) {

        ExchangeRate currencyRate = new ExchangeRate();
        currencyRate.setDate(date.getTime());
        currencyRate.setValue(valute.getValue());
        currencyRate.setCurrencyCode(CurrencyCode.codeOf(valute.getNumCode()));

        currencyConverterRepository.save(currencyRate);
    }

    private void saveAllCurrency(GregorianCalendar date, Valute[] valutes){
        new SaveAllCurrency().setDate(date).setValutes(valutes).startThread();
    }


    class SaveAllCurrency implements Runnable{

        private Valute[] valArray;
        private GregorianCalendar date;

        public SaveAllCurrency setValutes(Valute[] valutes){
            this.valArray = valutes;
            return this;
        }

        public SaveAllCurrency setDate(GregorianCalendar date){
            this.date = date;
            return this;
        }

        public void startThread(){
            new Thread(this).start();
        }

        @Override
        public void run() {
            for (CurrencyCode c : CurrencyCode.values()){

                if (c.getCode() == 643) continue;

                Valute valute =  Arrays.stream(valArray).
                        filter(e -> e.getNumCode() == c.getCode()).findFirst().
                        orElseThrow(() -> new IllegalArgumentException("does not find selected course for" +
                                c.getName()));

                save(valute, date);

            }
        }
    }

}