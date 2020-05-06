package com.github.geekuniversity_java_215.cmsbackend.core.entities.CurrencyConverter.sevices;

import com.github.geekuniversity_java_215.cmsbackend.core.data.enums.CurrencyCode;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.CurrencyConverter.pogo.ValCurs;
import com.github.geekuniversity_java_215.cmsbackend.core.entities.CurrencyConverter.pogo.Valute;
import jdk.internal.jline.internal.Nullable;
import lombok.Data;
import org.springframework.stereotype.Service;


import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.GregorianCalendar;
import java.util.Objects;

@Service
@Data
public class ValuteImportService {
    final private static String CBR_URL = "http://www.cbr.ru/scripts/XML_daily.asp?date_req=";

    private String getUrl(GregorianCalendar date) {
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        format.setCalendar(date);
        String dateString = format.format(date.getTime());

        return CBR_URL + dateString;
    }

    @Nullable
    private ValCurs requestValCurs(GregorianCalendar date) {

        try {

            String url = getUrl(date);
            URL url1 = new URL(url);

            JAXBContext jaxbContext = JAXBContext.newInstance(ValCurs.class);

            Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
            ValCurs curs = (ValCurs) jaxbUnmarshaller.unmarshal(url1);

            return curs;


        } catch (JAXBException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;

    }

    public Valute importValutes(GregorianCalendar date, CurrencyCode code){

        ValCurs curs = requestValCurs(date);

        if (Objects.isNull(curs)){
            throw  new IllegalArgumentException("not course on date" + date);
        }

        Valute[] valArray = curs.valutes.toArray(new Valute[0]);

        //TODO Переделать на сохранение в базу,

        Valute valute =  Arrays.stream(valArray).
                filter(e -> e.getNumCode() == code.getCode()).findFirst().
                orElseThrow(() -> new IllegalArgumentException("does not find selected course"));

        return valute;

    }

}