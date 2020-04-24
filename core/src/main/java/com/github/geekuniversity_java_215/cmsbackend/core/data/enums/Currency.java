package com.github.geekuniversity_java_215.cmsbackend.core.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import java.util.Objects;

@AllArgsConstructor
@Getter
@ToString
public enum Currency {
    RUB(643, "Российский рубль"),
    EUR(978, "Евро"),
    USD(840, "Доллар США");

    @ToString.Exclude
    private final int value;

    @ToString.Include
    private final String name;

    @NotNull
    public static Currency valueOf(int value) throws IllegalArgumentException {

        for (Currency currency : values()) {
            if (currency.value == value) {
                return currency;
            }
        }

        throw new IllegalArgumentException("No matching constant for [" + value + "]");

    }


    public boolean equals(Currency currency){

        if (Objects.nonNull(currency)) {
            return this.getValue() == currency.getValue();
        }

        return false;

    }

    // TODO: 24.04.2020 Подтянуть курсы валют "http://www.cbr.ru/scripts/XML_daily.asp?date_req=02/03/2002" 

}