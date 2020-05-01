package com.github.geekuniversity_java_215.cmsbackend.core.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.Map;

@AllArgsConstructor
@Getter
@ToString
public enum CurrencyCode {
    RUB(643, "Российский рубль", "none"),
    EUR(978, "Евро", "R01239"),
    USD(840, "Доллар США", "R01235");

    @ToString.Exclude
    private final int code;

    @ToString.Include
    private final String name;

    @ToString.Exclude
    private final String cbrId;

    private final static Map<Integer, CurrencyCode> ENUM_MAP = new HashMap<>();

    static {
        for(CurrencyCode item : CurrencyCode.values()) {
            ENUM_MAP.put(item.code, item);
        }
    }

    @NotNull
    public static CurrencyCode codeOf(int code) throws IllegalArgumentException {

        CurrencyCode result = ENUM_MAP.get(code);
        if (result == null) {
            throw new IllegalArgumentException("CurrencyCode - no matching constant for [" + code + "]");
        }
        return result;
    }


    // TODO: 24.04.2020 Подтянуть курсы валют "http://www.cbr.ru/scripts/XML_daily.asp?date_req=02/03/2002"
    // https://www.cbr-xml-daily.ru/daily_json.js

}